package ru.velkomfood.services.mrp4.watch.repository.base;

import org.mariadb.jdbc.MariaDbDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ru.velkomfood.services.mrp4.watch.Component;
import ru.velkomfood.services.mrp4.watch.bus.EventBus;
import ru.velkomfood.services.mrp4.watch.model.DataEntity;
import ru.velkomfood.services.mrp4.watch.model.master.Material;
import ru.velkomfood.services.mrp4.watch.model.master.Plant;
import ru.velkomfood.services.mrp4.watch.model.master.PurchaseGroup;
import ru.velkomfood.services.mrp4.watch.repository.DAO;
import ru.velkomfood.services.mrp4.watch.repository.DataManager;
import ru.velkomfood.services.mrp4.watch.repository.daoimpl.master.MaterialDao;
import ru.velkomfood.services.mrp4.watch.repository.daoimpl.master.PlantDao;
import ru.velkomfood.services.mrp4.watch.repository.daoimpl.master.PurchaseGroupDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DataManagerBase implements DataManager {

    private final Logger logger;
    private Properties dbProperties;
    private EventBus eventBus;
    private Sql2o sqlEngine;

    public DataManagerBase() {
        logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public Component readSettings() {

        dbProperties = new Properties();

        try (InputStream is = getClass().getResourceAsStream("/db.properties")) {
            dbProperties.load(is);
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }

        return this;
    }

    @Override
    public void rollInBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void configure() {

        MariaDbDataSource mds = new MariaDbDataSource();

        try {
            mds.setUrl(dbProperties.getProperty("url"));
            mds.setUser(dbProperties.getProperty("user"));
            mds.setPassword(dbProperties.getProperty("password"));
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }

        sqlEngine = new Sql2o(mds);
        createDatabaseStructureIfNotExists();

    }

    @Override
    public void saveData(String address) {

        if (!eventBus.queueIsEmpty(address)) {
            for (int idx = 0; idx < eventBus.queueSize(address); idx++) {
                eventBus.pull(address).ifPresent(this::save);
            }
        }

    }

    // private section

    private void createDatabaseStructureIfNotExists() {

        StringBuilder sb = new StringBuilder(0);

        try (InputStream is = getClass().getResourceAsStream("/db-init.sql")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                logger.info(line);
            }
            reader.close();
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        }

        String[] sqlQuery = sb.toString().split(";");

        try (Connection connection = sqlEngine.open()) {
            try (Statement statement = connection.getJdbcConnection().createStatement()) {
                for (String sqlRow : sqlQuery) {
                    statement.execute(sqlRow);
                }
            } catch (SQLException ex) {
                logger.error(ex.getMessage());
            }
        }

    }

    // Concrete method for the saving in the database
    private void save(DataEntity entity) {

        if (entity instanceof Plant) {
            var plant = (Plant) entity;
            DAO<Plant, String> plantDao = new PlantDao(sqlEngine);
            if (plantDao.exists(plant.getId())) {
                plantDao.update(plant);
            } else {
                plantDao.create(plant);
            }
        } else if (entity instanceof PurchaseGroup) {
            var purGroup = (PurchaseGroup) entity;
            DAO<PurchaseGroup, String> purGrpDao = new PurchaseGroupDao(sqlEngine);
            if (purGrpDao.exists(purGroup.getId())) {
                purGrpDao.update(purGroup);
            } else {
                purGrpDao.create(purGroup);
            }
        } else if (entity instanceof Material) {
            var material = (Material) entity;
            DAO<Material, Long> materialDao = new MaterialDao(sqlEngine);
            if (materialDao.exists(material.getId())) {
                materialDao.update(material);
            } else {
                materialDao.create(material);
            }
        }

    }

}
