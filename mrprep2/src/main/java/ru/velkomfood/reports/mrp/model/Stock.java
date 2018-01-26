package ru.velkomfood.reports.mrp.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Stock {

    private long materialId;
    private String warehouse;
    private String uom;
    private BigDecimal free;
    private BigDecimal quality;
    private BigDecimal block;

    public Stock() {
    }

    public Stock(long materialId, String warehouse, String uom,
                 BigDecimal free,
                 BigDecimal quality, BigDecimal block) {
        this.materialId = materialId;
        this.warehouse = warehouse;
        this.uom = uom;
        this.free = free;
        this.quality = quality;
        this.block = block;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getFree() {
        return free;
    }

    public void setFree(BigDecimal free) {
        this.free = free;
    }

    public BigDecimal getQuality() {
        return quality;
    }

    public void setQuality(BigDecimal quality) {
        this.quality = quality;
    }

    public BigDecimal getBlock() {
        return block;
    }

    public void setBlock(BigDecimal block) {
        this.block = block;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return materialId == stock.materialId &&
                Objects.equals(warehouse, stock.warehouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materialId, warehouse);
    }

}
