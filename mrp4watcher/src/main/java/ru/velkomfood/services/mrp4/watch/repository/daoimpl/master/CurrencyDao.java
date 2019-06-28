package ru.velkomfood.services.mrp4.watch.repository.daoimpl.master;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.velkomfood.services.mrp4.watch.model.master.Currency;
import ru.velkomfood.services.mrp4.watch.repository.DAO;

public class CurrencyDao implements DAO<Currency, String> {

    private final Sql2o sqlEngine;

    public CurrencyDao(Sql2o sqlEngine) {
        this.sqlEngine = sqlEngine;
    }

    @Override
    public boolean exists(String key) {

        boolean existence = false;

        try (Connection connection = sqlEngine.open()) {
            try (Query query = connection.createQuery(QUERY.CURR_EXIST.label)) {
                query.setAutoDeriveColumnNames(true);
                if (query.addParameter("key", key).executeScalar(Integer.class) > 0) {
                    existence = true;
                }
            }
        }

        return existence;
    }

    @Override
    public void create(Currency currency) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.CREATE_CURR.label)
                    .addParameter("id", currency.getId())
                    .addParameter("name", currency.getName())
                    .executeUpdate();
        }

    }

    @Override
    public void update(Currency currency) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.UPDATE_CURR.label)
                    .addParameter("name", currency.getName())
                    .addParameter("id", currency.getId())
                    .executeUpdate();
        }

    }

    // private section

    private enum QUERY {

        CURR_EXIST("SELECT COUNT( id ) FROM currency WHERE id = :key"),
        CREATE_CURR("INSERT INTO currency VALUES (:id, :name)"),
        UPDATE_CURR("UPDATE currency SET name = :name WHERE id = :id");

        private final String label;

        QUERY(String label) {
            this.label = label;
        }

    }

}
