package ru.velkomfood.sap.xml.storage.behavior;

import java.util.List;
import java.util.Optional;

public interface DAO<Entity, Key> {

    void create(Entity entity);
    Optional<Entity> findOne(Key key);
    List<Entity> findByKeyBetween(Key fromKey, Key toKey);
    List<Entity> findAll();
    void update(Entity entity);
    void delete(Entity entity);

}
