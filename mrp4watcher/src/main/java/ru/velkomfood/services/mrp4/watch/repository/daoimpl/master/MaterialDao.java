package ru.velkomfood.services.mrp4.watch.repository.daoimpl.master;

import org.sql2o.Sql2o;
import ru.velkomfood.services.mrp4.watch.model.master.Material;
import ru.velkomfood.services.mrp4.watch.repository.DAO;

public class MaterialDao implements DAO<Material, Long> {

    private final Sql2o sqlEngine;

    public MaterialDao(Sql2o sqlEngine) {
        this.sqlEngine = sqlEngine;
    }

    @Override
    public boolean exists(Long key) {
        return false;
    }

    @Override
    public void create(Material material) {

    }

    @Override
    public void update(Material material) {

    }

}
