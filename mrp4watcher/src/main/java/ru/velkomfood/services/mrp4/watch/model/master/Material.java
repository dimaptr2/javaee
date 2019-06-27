package ru.velkomfood.services.mrp4.watch.model.master;

import ru.velkomfood.services.mrp4.watch.model.DataEntity;

import java.util.Objects;

public class Material implements DataEntity {

    private long id;
    private String description;

    public Material() {
    }

    public Material(long id, String description) {
        this.id = id;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return id == material.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Material{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

}
