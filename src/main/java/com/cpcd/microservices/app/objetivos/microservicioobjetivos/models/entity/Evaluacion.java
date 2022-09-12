package com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "evaluacion")
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double nota;

    @JsonIgnoreProperties(value = {"evaluacion"})
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioLMS_id")
    private UsuarioLMS usuarioLMS;


    public Evaluacion(){}

    public Evaluacion(double nota) {
        this.nota = nota;
    }

    public Evaluacion(Long id, double nota) {
        this.id = id;
        this.nota = nota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public UsuarioLMS getUsuarioLMS() {
        return usuarioLMS;
    }

    public void setUsuarioLMS(UsuarioLMS usuarioLMS) {
        this.usuarioLMS = usuarioLMS;
    }
}
