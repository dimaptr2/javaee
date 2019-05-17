package ru.velkomfood.sap.xml.storage.repository;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ru.velkomfood.sap.xml.storage.behavior.DAO;
import ru.velkomfood.sap.xml.storage.model.Customer;

import java.util.List;
import java.util.Optional;

public class CustomerDao implements DAO<Customer, Long> {

    private final Sql2o sqlEngine;

    public CustomerDao(Sql2o sqlEngine) {
        this.sqlEngine = sqlEngine;
    }

    @Override
    public void create(Customer customer) {

        long id = customer.getId();
        String name = customer.getName();

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.CREATE_CUSTOMER.label)
                    .addParameter("id", id)
                    .addParameter("name", name)
                    .executeUpdate();
        }

    }

    @Override
    public Optional<Customer> findOne(Long id) {

        try (Connection connection = sqlEngine.open()) {
            List<Customer> customers = connection.createQuery(QUERY.READ_CUSTOMER.label)
                    .addParameter("id", id)
                    .executeAndFetch(Customer.class);
            return Optional.of(customers.get(0));
        }

    }

    @Override
    public List<Customer> findByKeyBetween(Long fromKey, Long toKey) {

        try (Connection connection = sqlEngine.open()) {
            return connection.createQuery(QUERY.READ_CUSTOMERS_BETWEEN.label)
                    .addParameter("fromKey", fromKey)
                    .addParameter("toKey", toKey)
                    .executeAndFetch(Customer.class);
        }

    }

    @Override
    public List<Customer> findAll() {

        try (Connection connection = sqlEngine.open()) {
            return connection.createQuery(QUERY.READ_ALL.label)
                    .executeAndFetch(Customer.class);
        }

    }

    @Override
    public void update(Customer customer) {

        long id = customer.getId();
        String name = customer.getName();

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.UPDATE_CUSTOMER.label)
                    .addParameter("name", name)
                    .addParameter("id", id)
                    .executeUpdate();
        }

    }

    @Override
    public void delete(Customer customer) {

        long id = customer.getId();

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.DELETE_CUSTOMER.label)
                    .addParameter("id", id)
                    .executeUpdate();
        }

    }

    // private section

    private enum QUERY {

        CREATE_CUSTOMER("INSERT INTO customers VALUES (:id, :name)"),
        READ_CUSTOMER("SELECT id, name FROM customers WHERE id = :id"),
        READ_CUSTOMERS_BETWEEN("SELECT id, name FROM customers WHERE id BETWEEN :fromKey AND :toKey ORDER BY id"),
        READ_ALL("SELECT id, name FROM customers ORDER BY id"),
        UPDATE_CUSTOMER("UPDATE customers SET name = :name WHERE id = :id"),
        DELETE_CUSTOMER("DELETE FROM customers WHERE id = :id");

        private String label;
        QUERY(String label) {
            this.label = label;
        }

    }

}
