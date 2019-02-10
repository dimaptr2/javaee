package ru.velkomfood.mrp.book.server.model;

import ru.velkomfood.mrp.book.server.model.keys.MrpInfoKey;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "mrp_info")
@IdClass(MrpInfoKey.class)
public class MrpInfoRow {

    // the primary key provided by MrpInfoKey class
    @Id
    private Integer plant;
    @Id
    @Column(name = "material_id")
    private Long materialId;
    @Id
    private Integer period;
    @Id
    private Integer year;
    @Id
    @Column(name = "prc_group", length = 3, nullable = false)
    private String purchaseGroup;

    @Column(name = "prc_group_name", length = 50)
    private String purchaseGroupName;
    @Column(length = 3)
    private String uom;

    @Column(precision = 20, scale = 3)
    private BigDecimal requirement;

    public MrpInfoRow() {
    }

    public MrpInfoRow(Integer plant, Long materialId, Integer period, Integer year,
                      String purchaseGroup, String purchaseGroupName, String uom, BigDecimal requirement) {
        this.plant = plant;
        this.materialId = materialId;
        this.period = period;
        this.year = year;
        this.purchaseGroup = purchaseGroup;
        this.purchaseGroupName = purchaseGroupName;
        this.uom = uom;
        this.requirement = requirement;
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

    public String getPurchaseGroupName() {
        return purchaseGroupName;
    }

    public void setPurchaseGroupName(String purchaseGroupName) {
        this.purchaseGroupName = purchaseGroupName;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getRequirement() {
        return requirement;
    }

    public void setRequirement(BigDecimal requirement) {
        this.requirement = requirement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MrpInfoRow that = (MrpInfoRow) o;
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
        return "MrpInfoRow{" +
                "plant=" + plant +
                ", materialId=" + materialId +
                ", period=" + period +
                ", year=" + year +
                ", purchaseGroup='" + purchaseGroup + '\'' +
                ", purchaseGroupName='" + purchaseGroupName + '\'' +
                ", uom='" + uom + '\'' +
                ", requirement=" + requirement +
                '}';
    }

}
