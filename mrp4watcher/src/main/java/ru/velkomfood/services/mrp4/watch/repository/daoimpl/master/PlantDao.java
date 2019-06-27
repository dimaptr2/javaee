package ru.velkomfood.services.mrp4.watch.repository.daoimpl.master;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ru.velkomfood.services.mrp4.watch.model.master.Plant;
import ru.velkomfood.services.mrp4.watch.repository.DAO;

public class PlantDao implements DAO<Plant, String> {

    private final Sql2o sqlEngine;

    public PlantDao(Sql2o sqlEngine) {
        this.sqlEngine = sqlEngine;
    }

    @Override
    public boolean exists(String key) {

        boolean result = false;

        try (Connection connection = sqlEngine.open()) {
            int counter = connection.createQueryWithParams(QUERY.PLANT_EXIST.label, key)
                    .addParameter("key", key)
                    .executeScalar(Integer.class);
            if (counter > 0) {
                result = true;
            }
        }

        return result;
    }

    @Override
    public void create(Plant plant) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.CREATE_PLANT.label)
                    .addParameter("id", plant.getId())
                    .addParameter("name",plant.getName())
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

        PLANT_EXIST("SELECT COUNT( id ) FROM plant WHERE id := key"),
        CREATE_PLANT("INSERT INTO plant VALUES (:id, :name"),
        UPDATE_PLANT("UPDATE plant SET name = :name WHERE id = :id");

        private final String label;

        QUERY(String label) {
            this.label = label;
        }

    }

}
