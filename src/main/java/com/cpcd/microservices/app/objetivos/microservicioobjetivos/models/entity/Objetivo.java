package com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "objetivo")
public class Objetivo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private int superado;

    private int posicion;

    @JsonIgnoreProperties(value = {"objetivos"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuarioLMS_id")
    private UsuarioLMS usuarioLMS;

    public Objetivo(String nombre, Integer posicion) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.superado = 0;
    }

    public Objetivo(String nombre, Integer posicion, Integer superado) {
        this.nombre = nombre;
        this.posicion = posicion;
        this.superado = superado;
    }

    public Objetivo(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Objetivo(Long id, String nombre, Integer superado) {
        this.id = id;
        this.nombre = nombre;
        this.superado = superado;
    }

    public Objetivo() {
    }

    public UsuarioLMS getUsuarioLMS() {
        return usuarioLMS;
    }

    public void setUsuarioLMS(UsuarioLMS usuarioLMS) {
        this.usuarioLMS = usuarioLMS;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSuperado() {
        return superado;
    }

    public void setSuperado(int superado) {
        this.superado = superado;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj){
            return true;
        }

        if (!(obj instanceof  Objetivo)){
            return false;
        }

        Objetivo o = (Objetivo) obj;

        return this.id != null && this.id.equals(o.getId());
    }

}
