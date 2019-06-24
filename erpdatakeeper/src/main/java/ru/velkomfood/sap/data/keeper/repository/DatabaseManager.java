package ru.velkomfood.sap.data.keeper.repository;

import ru.velkomfood.sap.data.keeper.config.Holder;

public interface DatabaseManager<DbSource> {
    void configure(DbSource source, Holder holder);
    void createDatabaseStructure();
}
