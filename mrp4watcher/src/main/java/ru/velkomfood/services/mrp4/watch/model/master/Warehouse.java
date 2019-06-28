package ru.velkomfood.services.mrp4.watch.model.master;

import ru.velkomfood.services.mrp4.watch.model.DataEntity;

import java.util.Objects;

public class Warehouse implements DataEntity {

    private String id;
    private String plantId;
    private String name;

    public Warehouse() {
    }

    public Warehouse(String id, String plantId, String name) {
        this.id = id;
        this.plantId = plantId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return id.equals(warehouse.id) &&
                plantId.equals(warehouse.plantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plantId);
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "id='" + id + '\'' +
                ", plantId='" + plantId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
