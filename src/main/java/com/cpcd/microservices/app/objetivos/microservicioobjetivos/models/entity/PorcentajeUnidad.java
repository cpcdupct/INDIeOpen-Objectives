package com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class PorcentajeUnidad implements Serializable {
    @NotEmpty
    private String unitid;

    @NotEmpty
    private int value;

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}