package ru.velkomfood.services.mrp4.watch.model.master;

import ru.velkomfood.services.mrp4.watch.model.DataEntity;

import java.util.Objects;

public class Period implements DataEntity {

    private int id;
    private String name;

    public Period() {
    }

    public Period(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        Period period = (Period) o;
        return id == period.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Period{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
