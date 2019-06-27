package ru.velkomfood.services.mrp4.watch.model.master;

import ru.velkomfood.services.mrp4.watch.model.DataEntity;

import java.util.Objects;

public class Plant implements DataEntity {

    private String id;
    private String name;

    public Plant() {
    }

    public Plant(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        Plant plant = (Plant) o;
        return id.equals(plant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Plant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
