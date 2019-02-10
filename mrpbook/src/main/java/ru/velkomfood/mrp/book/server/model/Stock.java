package ru.velkomfood.mrp.book.server.model;

import ru.velkomfood.mrp.book.server.model.keys.StockKey;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "stocks")
@IdClass(StockKey.class)
public class Stock implements Serializable {

    @Id
    private Integer plant;
    @Id
    @Column(name = "material_id")
    private Long materialId;
    @Id
    @Column(length = 4)
    private String warehouse;

    @Column(precision = 20, scale = 3)
    private BigDecimal quantity;

    public Stock() {
    }

    public Stock(Integer plant, Long materialId, String warehouse, BigDecimal quantity) {
        this.plant = plant;
        this.materialId = materialId;
        this.warehouse = warehouse;
        this.quantity = quantity;
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return plant.equals(stock.plant) &&
                materialId.equals(stock.materialId) &&
                warehouse.equals(stock.warehouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plant, materialId, warehouse);
    }

    @Override
    public String toString() {
        return "Stock{" +
                "plant=" + plant +
                ", materialId=" + materialId +
                ", warehouse='" + warehouse + '\'' +
                ", quantity=" + quantity +
                '}';
    }

}
