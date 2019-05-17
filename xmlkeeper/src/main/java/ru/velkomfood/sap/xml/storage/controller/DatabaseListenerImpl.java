package ru.velkomfood.sap.xml.storage.controller;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ru.velkomfood.sap.xml.storage.behavior.DatabaseListener;

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

    // private section

    private void initProvidersTable() {

    }

}
