package ru.velkomfood.mrp.book.server.model.keys;

import java.io.Serializable;
import java.util.Objects;

public class MrpInfoKey implements Serializable {

    private Integer plant;
    private Long materialId;
    private Integer period;
    private Integer year;
    private String purchaseGroup;

    public MrpInfoKey() {
    }

    public MrpInfoKey(Integer plant, Long materialId,
                      Integer period, Integer year, String purchaseGroup) {
        this.plant = plant;
        this.materialId = materialId;
        this.period = period;
        this.year = year;
        this.purchaseGroup = purchaseGroup;
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

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPurchaseGroup() {
        return purchaseGroup;
    }

    public void setPurchaseGroup(String purchaseGroup) {
        this.purchaseGroup = purchaseGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MrpInfoKey that = (MrpInfoKey) o;
        return plant.equals(that.plant) &&
                materialId.equals(that.materialId) &&
                period.equals(that.period) &&
                year.equals(that.year) &&
                purchaseGroup.equals(that.purchaseGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plant, materialId, period, year, purchaseGroup);
    }

    @Override
    public String toString() {
        return "MrpInfoKey{" +
                "plant=" + plant +
                ", materialId=" + materialId +
                ", period=" + period +
                ", year=" + year +
                ", purchaseGroup='" + purchaseGroup + '\'' +
                '}';
    }

}
