package ru.velkomfood.services.mrp4.watch.repository.daoimpl.master;

import org.sql2o.Connection;
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

        boolean existence = false;

        try (Connection connection = sqlEngine.open()) {
            long numberOfMaterials = connection.createQueryWithParams(QUERY.MATERIAL_EXIST.label, key)
                    .addParameter("key", key)
                    .executeScalar(Long.class);
            if (numberOfMaterials > 0) {
                existence = true;
            }
        }

        return existence;
    }

    @Override
    public void create(Material material) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.CREATE_MATERIAL.label)
                    .addParameter("id", material.getId())
                    .addParameter("description", material.getDescription())
                    .executeUpdate();
        }

    }

    @Override
    public void update(Material material) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.UPDATE_MATERIAL.label)
                    .addParameter("description", material.getDescription())
                    .addParameter("id", material.getId())
                    .executeUpdate();
        }

    }

    // private section

    private enum QUERY {

        MATERIAL_EXIST("SELECT COUNT( id ) FROM material WHERE id := key"),
        CREATE_MATERIAL("INSERT INTO material VALUES (:id, :description"),
        UPDATE_MATERIAL("UPDATE material SET description = :description WHERE id = :id");

        private final String label;

        QUERY(String label) {
            this.label = label;
        }

    }

}
