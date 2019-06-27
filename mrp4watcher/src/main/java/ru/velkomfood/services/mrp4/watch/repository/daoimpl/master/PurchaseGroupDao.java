package ru.velkomfood.services.mrp4.watch.repository.daoimpl.master;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import ru.velkomfood.services.mrp4.watch.model.master.PurchaseGroup;
import ru.velkomfood.services.mrp4.watch.repository.DAO;

public class PurchaseGroupDao implements DAO<PurchaseGroup, String> {

    private final Sql2o sqlEngine;

    public PurchaseGroupDao(Sql2o sqlEngine) {
        this.sqlEngine = sqlEngine;
    }

    @Override
    public boolean exists(String key) {

        boolean existence = false;

        try (Connection connection = sqlEngine.open()) {
            int numbElems = connection.createQueryWithParams(QUERY.PURGRP_EXIST.label, key)
                    .addParameter("key", key)
                    .executeScalar(Integer.class);
            if (numbElems > 0) {
                existence = true;
            }
        }

        return existence;
    }

    @Override
    public void create(PurchaseGroup purchaseGroup) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.CREATE_PURGRP.label)
                    .addParameter("id", purchaseGroup.getId())
                    .addParameter("name", purchaseGroup.getName())
                    .executeUpdate();
        }

    }

    @Override
    public void update(PurchaseGroup purchaseGroup) {

        try (Connection connection = sqlEngine.open()) {
            connection.createQuery(QUERY.UPDATE_PURGRP.label)
                    .addParameter("name", purchaseGroup.getName())
                    .addParameter("id", purchaseGroup.getId())
                    .executeUpdate();
        }

    }

    // private section

    private enum QUERY {

        PURGRP_EXIST("SELECT COUNT( id ) FROM pur_group WHERE id := key"),
        CREATE_PURGRP("INSERT INTO pur_group VALUES (:id, :name"),
        UPDATE_PURGRP("UPDATE pur_group SET name = :name WHERE id = :id");

        private final String label;

        QUERY(String label) {
            this.label = label;
        }

    }

}
