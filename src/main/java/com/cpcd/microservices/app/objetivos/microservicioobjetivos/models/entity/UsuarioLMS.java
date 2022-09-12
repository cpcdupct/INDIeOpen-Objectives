package com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "usuariolms")
public class UsuarioLMS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userid;

    private Long curso;

    private Long actividad;

    private String dominio;

    private String idunidad;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_at")
    private Date createAt;

    @JsonIgnoreProperties(value = {"usuarioLMS"}, allowSetters = true)
    @OneToMany(mappedBy = "usuarioLMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Objetivo> objetivos;

    @JsonIgnoreProperties(value = {"usuarioLMS"}, allowSetters = true)
    @OneToOne(mappedBy = "usuarioLMS", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Evaluacion evaluacion;


    public UsuarioLMS() {
        this.objetivos = new ArrayList<>();
    }

    public UsuarioLMS(Long userid, Long curso, Long actividad, String dominio, String idunidad) {
        this.userid = userid;
        this.curso = curso;
        this.actividad = actividad;
        this.dominio = dominio;

        this.objetivos = new ArrayList<>();
        this.idunidad = idunidad;
    }

    @PrePersist
    public void prePersist(){
        this.createAt = new Date();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getCurso() {
        return curso;
    }

    public void setCurso(Long curso) {
        this.curso = curso;
    }

    public Long getActividad() {
        return actividad;
    }

    public void setActividad(Long actividad) {
        this.actividad = actividad;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getIdunidad() {
        return idunidad;
    }

    public void setIdunidad(String idunidad) {
        this.idunidad = idunidad;
    }

    public List<Objetivo> getObjetivos() {
        return objetivos;
    }

    public boolean comprobarCambios(List<Objetivo> objetivos){
        return this.objetivos.equals(objetivos);
    }

    public void setObjetivos(List<Objetivo> objetivos) {
        this.objetivos.clear();
        objetivos.forEach(p ->{
            this.addObjetivo(p);
            p.setUsuarioLMS(this);
        });
    }

    public void addObjetivo(Objetivo objetivo) {
        this.objetivos.add(objetivo);
        objetivo.setUsuarioLMS(this);
    }

    public void removeObjetivo(Objetivo objetivo) {
        this.objetivos.remove(objetivo);
        objetivo.setUsuarioLMS(null);
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
        evaluacion.setUsuarioLMS(this);
    }


    @Override
    public boolean equals(Object obj) {

        if (this == obj){
            return true;
        }

        if (!(obj instanceof  UsuarioLMS)){
            return false;
        }

        Objetivo o = (Objetivo) obj;

        return this.id != null && this.id.equals(o.getId());
    }
}
