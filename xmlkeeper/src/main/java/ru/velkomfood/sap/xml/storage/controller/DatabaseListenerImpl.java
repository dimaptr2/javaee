package ru.velkomfood.sap.xml.storage.controller;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ru.velkomfood.sap.xml.storage.behavior.DAO;
import ru.velkomfood.sap.xml.storage.behavior.DatabaseListener;
import ru.velkomfood.sap.xml.storage.model.Customer;
import ru.velkomfood.sap.xml.storage.model.MessageKey;
import ru.velkomfood.sap.xml.storage.model.Provider;
import ru.velkomfood.sap.xml.storage.model.SoapMessage;
import ru.velkomfood.sap.xml.storage.repository.CustomerDao;
import ru.velkomfood.sap.xml.storage.repository.ProviderDao;
import ru.velkomfood.sap.xml.storage.repository.SoapMessageDao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseListenerImpl implements DatabaseListener {

    private final Properties queries;
    private final Sql2o sqlManager;

    public DatabaseListenerImpl(Properties queries) {
        this.queries = queries;
        sqlManager = new Sql2o(queries.getProperty("url"), queries.getProperty("user"), queries.getProperty("password"));
    }

    @Override
    public void initialize() {

        try {
            String tablesCreation = new String(Files.readAllBytes(Paths.get(queries.getProperty("init.file"))));
            String[] sqlCommands = tablesCreation.split(";");
            try (Connection connection = sqlManager.open()) {
                try (Statement statement = connection.getJdbcConnection().createStatement()) {
                   for (String sql : sqlCommands) {
                       sql = sql.replaceFirst("\n", "");
                       statement.execute(sql);
                       LOGGER.info(sql);
                   }
                } catch (SQLException sqlEx) {
                    LOGGER.error(sqlEx.getMessage());
                }
            }
            initProvidersTable();
        } catch (IOException ioe) {
            LOGGER.error(ioe.getMessage());
        }

    }

    @Override
    public void createCustomerEntity(Customer customer) {

        DAO<Customer, Long> dao = new CustomerDao(sqlManager);
        if (!dao.exists(customer)) {
            dao.create(customer);
        }

    }

    @Override
    public void saveSoapMessageEntity(SoapMessage message) {

        DAO<SoapMessage, MessageKey> dao = new SoapMessageDao(sqlManager);
        if (dao.exists(message)) {
            dao.update(message);
        } else {
            dao.create(message);
        }

    }

    // private section

    private void initProvidersTable() {

        DAO<Provider, Long> dao = new ProviderDao(sqlManager);
        Provider provider1 = new Provider(1, "EDI Soft");
        if (!dao.exists(provider1)) {
            dao.create(provider1);
        }

        Provider provider2 = new Provider(2, "Korus");
        if (!dao.exists(provider2)) {
            dao.create(provider2);
        }

    }

}
