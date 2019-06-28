package ru.velkomfood.services.mrp4.watch.repository.daoimpl.master;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.velkomfood.services.mrp4.watch.model.master.Measure;
import ru.velkomfood.services.mrp4.watch.repository.DAO;

public class MeasureDao implements DAO<Measure, String> {

    private final Sql2o sqlEngine;

    public MeasureDao(Sql2o sqlEngine) {
        this.sqlEngine = sqlEngine;
    }

    @Override
    public boolean exists(String key) {

        boolean existence = false;

        try (Connection connection = sqlEngine.open()) {
            try (Query query = connection.createQuery(QUERY.MES_EXIST.label)) {
                query.setAutoDeriveColumnNames(true);
                if (query.addParameter("key", key).executeScalar(Integer.class) > 0) {
                    existence = true;
                }
            }
        }

        return existence;
    }

    @Override
    public void create(Measure measure) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.CREATE_MES.label)
                    .addParameter("id", measure.getId())
                    .addParameter("name", measure.getName())
                    .executeUpdate();
        }

    }

    @Override
    public void update(Measure measure) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.UPDATE_MES.label)
                    .addParameter("name", measure.getName())
                    .addParameter("id", measure.getId())
                    .executeUpdate();
        }

    }

    // private section

    private enum QUERY {

        MES_EXIST("SELECT COUNT( id ) FROM measure WHERE id = :key"),
        CREATE_MES("INSERT INTO measure VALUES (:id, :name)"),
        UPDATE_MES("UPDATE measure SET name = :name WHERE id = :id");

        private final String label;

        QUERY(String label) {
            this.label = label;
        }

    }

}
