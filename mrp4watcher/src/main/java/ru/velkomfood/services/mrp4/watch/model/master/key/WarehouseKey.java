package ru.velkomfood.services.mrp4.watch.model.master.key;

import ru.velkomfood.services.mrp4.watch.model.DataEntity;

import java.util.Objects;

public class WarehouseKey implements DataEntity {

    private String id;
    private String plantId;

    public WarehouseKey() {
    }

    public WarehouseKey(String id, String plantId) {
        this.id = id;
        this.plantId = plantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WarehouseKey that = (WarehouseKey) o;
        return id.equals(that.id) &&
                plantId.equals(that.plantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plantId);
    }

    @Override
    public String toString() {
        return "WarehouseKey{" +
                "id='" + id + '\'' +
                ", plantId='" + plantId + '\'' +
                '}';
    }

}
