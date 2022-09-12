package com.cpcd.microservices.app.objetivos.microservicioobjetivos.services;

import com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity.Objetivo;
import com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity.PorcentajeCurso;
import com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity.UsuarioLMS;

import java.util.List;
import java.util.Optional;

public interface ServicioObjetivo {
    public Iterable<UsuarioLMS> findAll();

    public Optional<UsuarioLMS> findById(Long id);

    public Optional<UsuarioLMS> findByUseridCursoActividadDominioIdunidad(Long userid, Long curso, Long actividad, String dominio, String idunidad);

    public PorcentajeCurso findByUseridCursoActividadDominio(Long userid, Long curso, Long actividad, String dominio);

    public UsuarioLMS save(UsuarioLMS usuarioLMS);

    public void deleteById(Long id);

    public Optional<Objetivo> findObjetivoById(Long id);

    public Objetivo saveObjetivo(Objetivo objetivo);

    public boolean compararListas(List<Objetivo> lista1,  List<Objetivo> lista2);
}
