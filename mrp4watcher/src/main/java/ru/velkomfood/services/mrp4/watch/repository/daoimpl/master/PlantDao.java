package ru.velkomfood.services.mrp4.watch.repository.daoimpl.master;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.velkomfood.services.mrp4.watch.model.master.Plant;
import ru.velkomfood.services.mrp4.watch.repository.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlantDao implements DAO<Plant, String> {

    private final Sql2o sqlEngine;
    private final Logger logger;

    public PlantDao(Sql2o sqlEngine) {
        this.sqlEngine = sqlEngine;
        logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public boolean exists(String key) {

        boolean existence = false;

        try (Connection connection = sqlEngine.open()) {
            try (Query query = connection.createQuery(QUERY.PLANT_EXIST.label)) {
                query.setAutoDeriveColumnNames(true);
                if (query.addParameter("key", key).executeScalar(Integer.class) > 0) {
                    existence = true;
                }
            }
        }

        return existence;
    }

    @Override
    public void create(Plant plant) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.CREATE_PLANT.label)
                    .addParameter("id", plant.getId())
                    .addParameter("name", plant.getName())
                    .executeUpdate();
        }

    }

    @Override
    public void update(Plant plant) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.UPDATE_PLANT.label)
                    .addParameter("name", plant.getName())
                    .addParameter("id", plant.getId())
                    .executeUpdate();
        }

    }

    private enum QUERY {

        PLANT_EXIST("SELECT COUNT( id ) FROM plant WHERE id = :key"),
        CREATE_PLANT("INSERT INTO plant VALUES (:id, :name)"),
        UPDATE_PLANT("UPDATE plant SET name = :name WHERE id = :id");

        private final String label;

        QUERY(String label) {
            this.label = label;
        }

    }

}
