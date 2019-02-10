package ru.velkomfood.mrp.book.server.model.keys;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class StockKey implements Serializable {

    private Integer plant;
    private Long materialId;
    private String warehouse;

    public StockKey() {
    }

    public StockKey(Integer plant, Long materialId, String warehouse) {
        this.plant = plant;
        this.materialId = materialId;
        this.warehouse = warehouse;
    }

    public Integer getPlant() {
        return plant;
    }

    public void setPlant(Integer plant) {
        this.plant = plant;
    }

    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockKey stockKey = (StockKey) o;
        return plant.equals(stockKey.plant) &&
                materialId.equals(stockKey.materialId) &&
                warehouse.equals(stockKey.warehouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plant, materialId, warehouse);
    }

    @Override
    public String toString() {
        return "StockKey{" +
                "plant=" + plant +
                ", materialId=" + materialId +
                ", warehouse='" + warehouse + '\'' +
                '}';
    }

}
