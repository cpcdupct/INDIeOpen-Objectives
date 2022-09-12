package com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.repository;



import com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity.Objetivo;
import org.springframework.data.repository.CrudRepository;

public interface ObjetivoRepository  extends CrudRepository<Objetivo, Long> {
}
