package ru.velkomfood.services.mrp4.watch.repository.base;

import org.mariadb.jdbc.MariaDbDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ru.velkomfood.services.mrp4.watch.Component;
import ru.velkomfood.services.mrp4.watch.bus.EventBus;
import ru.velkomfood.services.mrp4.watch.model.DataEntity;
import ru.velkomfood.services.mrp4.watch.model.master.*;
import ru.velkomfood.services.mrp4.watch.model.master.key.WarehouseKey;
import ru.velkomfood.services.mrp4.watch.repository.DAO;
import ru.velkomfood.services.mrp4.watch.repository.DataManager;
import ru.velkomfood.services.mrp4.watch.repository.daoimpl.master.*;

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
        createDatabaseStructureIfNotExist();
        createOrganizationalUnits();
        createCurrencyMasterData();

    }

    @Override
    public void saveData(String address) {

        if (!eventBus.queueIsEmpty(address)) {
            final int limit = eventBus.queueSize(address);
            for (int idx = 0; idx < limit; idx++) {
                eventBus.pull(address).ifPresent(this::save);
            }
        }

    }

    // private section

    private void createDatabaseStructureIfNotExist() {

        StringBuilder sb = new StringBuilder(0);

        try (InputStream is = getClass().getResourceAsStream("/db-init.sql")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
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

    private void createOrganizationalUnits() {

        DAO<Plant, String> plantDao = new PlantDao(sqlEngine);
        Plant plant = new Plant("1000", "ООО МК Павловская Слобода");
        if (!plantDao.exists(plant.getId())) {
            plantDao.create(plant);
        }

        Period[] periods = buildPeriods();
        DAO<Period, Integer> periodDao = new PeriodDao(sqlEngine);

        for (Period periodValue : periods) {
            if (!periodDao.exists(periodValue.getId())) {
                periodDao.create(periodValue);
            }
        }

    }

    private Period[] buildPeriods() {
        return new Period[] {
                new Period(1, "Январь"),
                new Period(2, "Февраль"),
                new Period(3, "Март"),
                new Period(4, "Апрель"),
                new Period(5, "Май"),
                new Period(6, "Июнь"),
                new Period(7, "Июль"),
                new Period(8, "Август"),
                new Period(9, "Сентябрь"),
                new Period(10, "Октябрь"),
                new Period(11, "Ноябрь"),
                new Period(12, "Декабрь")
        };
    }

    private void createCurrencyMasterData() {

        // Add currencies
        DAO<Currency, String> currencyDao = new CurrencyDao(sqlEngine);

        Currency euro = new Currency("EUR", "Евро");
        if (!currencyDao.exists(euro.getId())) {
            currencyDao.create(euro);
        }

        Currency rub = new Currency("RUB", "Рубль РФ");
        if (!currencyDao.exists(rub.getId())) {
            currencyDao.create(rub);
        }

        Currency usd = new Currency("USD", "Доллар США");
        if (!currencyDao.exists(usd.getId())) {
            currencyDao.create(usd);
        }

    }

    // Concrete method for the saving in the database
    private void save(DataEntity entity) {

        if (entity instanceof PurchaseGroup) {
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
        } else if (entity instanceof Measure) {
            var measure = (Measure) entity;
            DAO<Measure, String> measureDao = new MeasureDao(sqlEngine);
            if (measureDao.exists(measure.getId())) {
                measureDao.update(measure);
            } else {
                measureDao.create(measure);
            }
        } else if (entity instanceof Warehouse) {
            var whs = (Warehouse) entity;
            DAO<Warehouse, WarehouseKey> warehouseDao = new WarehouseDao(sqlEngine);
            WarehouseKey key = new WarehouseKey(whs.getId(), whs.getPlantId());
            if (warehouseDao.exists(key)) {
                warehouseDao.update(whs);
            } else {
                warehouseDao.create(whs);
            }
        }

    }


}
