package ru.velkomfood.services.mrp4.watch.repository;

public interface DAO<Entity, Key> {

    boolean exists(Key key);
    void create(Entity entity);
    void update(Entity entity);

}
