package ru.velkomfood.reports.mrp.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OutputView {

    private String materialId;
    private String description;
    private String warehouse;
    private String purchaseGroup;
    private String name;
    private String uom;
    private BigDecimal free;
    private BigDecimal quality;
    private BigDecimal block;
    // MRP data
    private List<BigDecimal> requirements;

    public OutputView() {
        requirements = new ArrayList<>();
    }

    public OutputView(String materialId, String description,
                      String warehouse, String purchaseGroup,
                      String name, String uom, BigDecimal free,
                      BigDecimal quality, BigDecimal block) {
        this.materialId = materialId;
        this.description = description;
        this.warehouse = warehouse;
        this.purchaseGroup = purchaseGroup;
        this.name = name;
        this.uom = uom;
        this.free = free;
        this.quality = quality;
        this.block = block;
        requirements = new ArrayList<>();
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getPurchaseGroup() {
        return purchaseGroup;
    }

    public void setPurchaseGroup(String purchaseGroup) {
        this.purchaseGroup = purchaseGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<BigDecimal> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<BigDecimal> requirements) {
        this.requirements = requirements;
    }

    public void addRequirement(BigDecimal value) {
        requirements.add(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutputView that = (OutputView) o;
        return Objects.equals(materialId, that.materialId) &&
                Objects.equals(warehouse, that.warehouse) &&
                Objects.equals(purchaseGroup, that.purchaseGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materialId, warehouse, purchaseGroup);
    }

}
