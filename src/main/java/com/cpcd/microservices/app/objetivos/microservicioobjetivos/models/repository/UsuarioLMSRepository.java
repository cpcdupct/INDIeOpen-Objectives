package com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.repository;

import com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity.UsuarioLMS;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioLMSRepository extends CrudRepository <UsuarioLMS, Long> {

    public List<UsuarioLMS> findByUseridAndCursoAndActividadAndDominio(Long userid, Long curso, Long actividad, String dominio);

    public Optional<UsuarioLMS> findByUseridAndCursoAndActividadAndDominioAndIdunidad(Long userid, Long curso, Long actividad, String dominio, String idunidad);

}
