package ru.velkomfood.services.mrp4.watch.repository.daoimpl.master;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;
import ru.velkomfood.services.mrp4.watch.model.master.Warehouse;
import ru.velkomfood.services.mrp4.watch.model.master.key.WarehouseKey;
import ru.velkomfood.services.mrp4.watch.repository.DAO;

public class WarehouseDao implements DAO<Warehouse, WarehouseKey> {

    private final Sql2o sqlEngine;

    public WarehouseDao(Sql2o sqlEngine) {
        this.sqlEngine = sqlEngine;
    }

    @Override
    public boolean exists(WarehouseKey warehouseKey) {

        boolean existence = false;

        try (Connection connection = sqlEngine.open()) {
            try (Query query = connection.createQuery(QUERY.WHS_EXIST.label)) {
                query.setAutoDeriveColumnNames(true);
                if (query
                        .addParameter("id", warehouseKey.getId())
                        .addParameter("plantId", warehouseKey.getPlantId())
                        .executeScalar(Integer.class) > 0) {
                    existence = true;
                }
            }
        }

        return existence;
    }

    @Override
    public void create(Warehouse warehouse) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.CREATE_WHS.label)
                    .addParameter("id", warehouse.getId())
                    .addParameter("plantId", warehouse.getPlantId())
                    .addParameter("name", warehouse.getName())
                    .executeUpdate();
        }

    }

    @Override
    public void update(Warehouse warehouse) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.UPDATE_WHS.label)
                    .addParameter("plantId", warehouse.getPlantId())
                    .addParameter("name", warehouse.getName())
                    .addParameter("id", warehouse.getId())
                    .executeUpdate();
        }

    }

    // private section

    private enum QUERY {

        WHS_EXIST("SELECT COUNT( id ) FROM warehouse WHERE id = :id AND plant_id = :plantId"),
        CREATE_WHS("INSERT INTO warehouse VALUES (:id, :plantId, :name)"),
        UPDATE_WHS("UPDATE warehouse SET plant_id = :plantId, name = :name WHERE id = :id");

        private final String label;

        QUERY(String label) {
            this.label = label;
        }

    }

}
