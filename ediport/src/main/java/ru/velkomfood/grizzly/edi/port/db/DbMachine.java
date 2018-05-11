package ru.velkomfood.grizzly.edi.port.db;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import ru.velkomfood.grizzly.edi.port.PropertiesReader;

import java.sql.SQLException;
import java.util.Map;

public class DbMachine {

    private static final DbMachine instance = new DbMachine();
    private PropertiesReader propertiesReader;
    private JdbcConnectionSource connectionSource;

    public static DbMachine create() {
        return instance;
    }

    public void setPropertiesReader(PropertiesReader propertiesReader) {
        this.propertiesReader = propertiesReader;
    }

    public void initDatabaseStatus() throws SQLException {

        Map<String, String> dbParams = propertiesReader.readFileIntoMap("/data-model.properties");

        connectionSource = new JdbcConnectionSource(dbParams.get("url"));
        connectionSource.setUsername(dbParams.get("user"));
        connectionSource.setPassword(dbParams.get("password"));

    }

}
