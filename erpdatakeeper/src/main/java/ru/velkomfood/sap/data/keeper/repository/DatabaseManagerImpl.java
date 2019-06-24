package ru.velkomfood.sap.data.keeper.repository;

import org.slf4j.Logger;
import org.sql2o.Sql2o;
import ru.velkomfood.sap.data.keeper.config.Holder;

import javax.sql.DataSource;

public class DatabaseManagerImpl implements DatabaseManager<DataSource> {

    private final Logger logger;
    private Sql2o sqlEngine;
    private Holder holder;

    public DatabaseManagerImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    public void configure(DataSource dataSource, Holder holder) {
        sqlEngine = new Sql2o(dataSource);
        this.holder = holder;
    }

    @Override
    public void createDatabaseStructure() {

    }

}
