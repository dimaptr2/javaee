package ru.velkomfood.mrp.book.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "materials")
public class MaterialMasterRow implements Serializable {

    @Id
    private Long id;
    @Column(length = 50)
    private String description;
    @Column(length = 3)
    private String uom;

    public MaterialMasterRow() {
    }

    public MaterialMasterRow(Long id, String description, String uom) {
        this.id = id;
        this.description = description;
        this.uom = uom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MaterialMasterRow that = (MaterialMasterRow) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MaterialMasterRow{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", uom='" + uom + '\'' +
                '}';
    }

}
