package com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PorcentajeCurso implements Serializable {
    @NotEmpty
    private List<PorcentajeUnidad> porcentaje;

    public PorcentajeCurso() {
        this.porcentaje = new ArrayList<>();
    }

    public List<PorcentajeUnidad> getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(List<PorcentajeUnidad> porcentaje) {
        this.porcentaje = porcentaje;
    }

    public void addUnidad(PorcentajeUnidad porcentajeUnidad) {
        this.porcentaje.add(porcentajeUnidad);
    }
}
