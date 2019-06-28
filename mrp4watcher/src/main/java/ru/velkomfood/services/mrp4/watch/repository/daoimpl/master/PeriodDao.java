package ru.velkomfood.services.mrp4.watch.repository.daoimpl.master;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.velkomfood.services.mrp4.watch.model.master.Period;
import ru.velkomfood.services.mrp4.watch.repository.DAO;

public class PeriodDao implements DAO<Period, Integer> {

    private final Sql2o sqlEngine;

    public PeriodDao(Sql2o sqlEngine) {
        this.sqlEngine = sqlEngine;
    }

    @Override
    public boolean exists(Integer key) {

        boolean existence = false;

        try (Connection connection = sqlEngine.open()) {
            try (Query query = connection.createQuery(QUERY.PERIOD_EXIST.label)) {
                query.setAutoDeriveColumnNames(true);
                if (query.addParameter("key", key).executeScalar(Integer.class) > 0) {
                    existence = true;
                }
            }
        }

        return existence;
    }

    @Override
    public void create(Period period) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.CREATE_PERIOD.label)
                    .addParameter("id", period.getId())
                    .addParameter("name", period.getName())
                    .executeUpdate();
        }

    }

    @Override
    public void update(Period period) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.UPDATE_PERIOD.label)
                    .addParameter("name", period.getName())
                    .addParameter("id", period.getId())
                    .executeUpdate();
        }

    }

    // private section

    private enum QUERY {

        PERIOD_EXIST("SELECT COUNT( id ) FROM period WHERE id = :key"),
        CREATE_PERIOD("INSERT INTO period VALUES (:id, :name)"),
        UPDATE_PERIOD("UPDATE period SET name = :name WHERE id = :id");

        private final String label;

        QUERY(String label) {
            this.label = label;
        }
    }

}
