package ru.velkomfood.reports.mrp.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Requirement {

    private long materialId;
    private String purchaseGroup;
    private int year;
    private String uom;
    // Quantities
    private BigDecimal req01;
    private BigDecimal req02;
    private BigDecimal req03;
    private BigDecimal req04;
    private BigDecimal req05;
    private BigDecimal req06;
    private BigDecimal req07;
    private BigDecimal req08;
    private BigDecimal req09;
    private BigDecimal req10;
    private BigDecimal req11;
    private BigDecimal req12;

    public Requirement() {
    }

    public Requirement(long materialId, String purchaseGroup, int year, String uom) {
        this.materialId = materialId;
        this.purchaseGroup = purchaseGroup;
        this.year = year;
        this.uom = uom;
    }

    public long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(long materialId) {
        this.materialId = materialId;
    }


    public String getPurchaseGroup() {
        return purchaseGroup;
    }

    public void setPurchaseGroup(String purchaseGroup) {
        this.purchaseGroup = purchaseGroup;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public BigDecimal getReq01() {
        return req01;
    }

    public void setReq01(BigDecimal req01) {
        this.req01 = req01;
    }

    public BigDecimal getReq02() {
        return req02;
    }

    public void setReq02(BigDecimal req02) {
        this.req02 = req02;
    }

    public BigDecimal getReq03() {
        return req03;
    }

    public void setReq03(BigDecimal req03) {
        this.req03 = req03;
    }

    public BigDecimal getReq04() {
        return req04;
    }

    public void setReq04(BigDecimal req04) {
        this.req04 = req04;
    }

    public BigDecimal getReq05() {
        return req05;
    }

    public void setReq05(BigDecimal req05) {
        this.req05 = req05;
    }

    public BigDecimal getReq06() {
        return req06;
    }

    public void setReq06(BigDecimal req06) {
        this.req06 = req06;
    }

    public BigDecimal getReq07() {
        return req07;
    }

    public void setReq07(BigDecimal req07) {
        this.req07 = req07;
    }

    public BigDecimal getReq08() {
        return req08;
    }

    public void setReq08(BigDecimal req08) {
        this.req08 = req08;
    }

    public BigDecimal getReq09() {
        return req09;
    }

    public void setReq09(BigDecimal req09) {
        this.req09 = req09;
    }

    public BigDecimal getReq10() {
        return req10;
    }

    public void setReq10(BigDecimal req10) {
        this.req10 = req10;
    }

    public BigDecimal getReq11() {
        return req11;
    }

    public void setReq11(BigDecimal req11) {
        this.req11 = req11;
    }

    public BigDecimal getReq12() {
        return req12;
    }

    public void setReq12(BigDecimal req12) {
        this.req12 = req12;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Requirement that = (Requirement) o;
        return materialId == that.materialId &&
                year == that.year &&
                Objects.equals(purchaseGroup, that.purchaseGroup);
    }

    @Override
    public int hashCode() {
        return Objects.hash(materialId, purchaseGroup, year);
    }

}
