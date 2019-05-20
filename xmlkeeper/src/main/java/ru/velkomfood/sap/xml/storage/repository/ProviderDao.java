package ru.velkomfood.sap.xml.storage.repository;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ru.velkomfood.sap.xml.storage.behavior.DAO;
import ru.velkomfood.sap.xml.storage.model.Provider;

import java.util.List;
import java.util.Optional;

public class ProviderDao implements DAO<Provider, Long> {

    private final Sql2o sqlEngine;

    public ProviderDao(Sql2o sqlEngine) {
        this.sqlEngine = sqlEngine;
    }

    @Override
    public boolean exists(Provider provider) {

        long id = provider.getId();
        int counter;

        try (Connection connection = sqlEngine.open()) {
            counter = connection.createQuery(QUERY.COUNT_PROVIDERS.label)
                    .addParameter("id", id)
                    .executeScalar(Integer.class);
        }

        if (counter > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void create(Provider provider) {

        try (Connection connection = sqlEngine.open()) {
            long id = provider.getId();
            String name = provider.getName();
            connection.createQuery(QUERY.CREATE_PROVIDER.label)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .executeUpdate();
        }

    }

    @Override
    public Optional<Provider> findOne(Long id) {

        Optional<Provider> optionalProvider;

        try (Connection connection = sqlEngine.open()) {
            Provider provider = connection.createQuery(QUERY.READ_PROVIDER.label)
                    .addParameter("id", id)
                    .executeScalar(Provider.class);
            optionalProvider = Optional.of(provider);
        }

        return optionalProvider;
    }

    @Override
    public List<Provider> findByKeyBetween(Long fromKey, Long toKey) {

        try (Connection connection = sqlEngine.open()) {
            return connection.createQuery(QUERY.READ_PROVIDERS_BETWEEN.label)
                    .addParameter("fromKey", fromKey)
                    .addParameter("toKey", toKey)
                    .executeAndFetch(Provider.class);
        }

    }

    @Override
    public List<Provider> findAll() {

        try (Connection connection = sqlEngine.open()) {
            return connection.createQuery(QUERY.READ_ALL.label)
                    .executeAndFetch(Provider.class);
        }

    }

    @Override
    public void update(Provider provider) {

        try (Connection connection = sqlEngine.open()) {
            long id = provider.getId();
            String name = provider.getName();
            connection.createQuery(QUERY.UPDATE_PROVIDER.label)
                    .addParameter("name", name)
                    .addParameter("id", id)
                    .executeUpdate();
        }

    }

    @Override
    public void delete(Provider provider) {

        try (Connection connection = sqlEngine.open()) {
            long id = provider.getId();
            connection.createQuery(QUERY.DELETE_PROVIDER.label)
                    .addParameter("id", id)
                    .executeUpdate();
        }

    }

    // private section

    private enum QUERY {

        COUNT_PROVIDERS("SELECT COUNT( id ) FROM providers WHERE id = :id"),
        CREATE_PROVIDER("INSERT INTO providers VALUES (:id, :name)"),
        READ_PROVIDER("SELECT id, name FROM providers WHERE id = :id"),
        READ_PROVIDERS_BETWEEN("SELECT id, name FROM providers WHERE id BETWEEN :fromKey AND :toKey ORDER BY id"),
        READ_ALL("SELECT id, name FROM providers ORDER BY id"),
        UPDATE_PROVIDER("UPDATE providers SET name = :name WHERE id = :id"),
        DELETE_PROVIDER("DELETE FROM providers WHERE id = :id");

        private String label;

        QUERY(String label) {
            this.label = label;
        }

    }

}
