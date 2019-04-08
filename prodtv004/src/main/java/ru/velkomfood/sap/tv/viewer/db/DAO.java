package ru.velkomfood.sap.tv.viewer.db;

import java.util.Optional;

public interface DAO<Entity, Key> {

    void create(Entity model);
    Optional<Entity> findOne(Key key);
    boolean entityExists(Entity model);
    void update(Entity model);
    void delete(Entity model);

}
