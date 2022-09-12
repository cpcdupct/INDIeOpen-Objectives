package com.cpcd.microservices.app.objetivos.microservicioobjetivos.services;


import com.cpcd.microservices.app.objetivos.microservicioobjetivos.controller.ControllerObjetivo;
import com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity.Objetivo;
import com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity.PorcentajeCurso;
import com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity.PorcentajeUnidad;
import com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity.UsuarioLMS;
import com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.repository.ObjetivoRepository;
import com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.repository.UsuarioLMSRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ServicioImplObjetivo implements ServicioObjetivo{

    @Autowired
    private UsuarioLMSRepository repository;

    @Autowired
    private ObjetivoRepository objetivoRepository;

    @Override
    @Transactional(readOnly = true)
    public Iterable<UsuarioLMS> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioLMS> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Objetivo> findObjetivoById(Long id) {
        return objetivoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioLMS> findByUseridCursoActividadDominioIdunidad(Long userid, Long curso, Long actividad, String dominio, String idunidad) {
        return repository.findByUseridAndCursoAndActividadAndDominioAndIdunidad(userid,curso,actividad,dominio,idunidad);
    }

    @Override
    @Transactional(readOnly = true)
    public PorcentajeCurso findByUseridCursoActividadDominio(Long userid, Long curso, Long actividad, String dominio) {
        PorcentajeCurso pc = new PorcentajeCurso();
        List<UsuarioLMS> lulms = repository.findByUseridAndCursoAndActividadAndDominio(userid,curso,actividad,dominio);
        if (lulms.isEmpty()) {
            return pc;
        }

        for( UsuarioLMS ulms: lulms){
            PorcentajeUnidad pu = new PorcentajeUnidad();
            pu.setUnitid(ulms.getIdunidad());
            int poracum = 0;
            int contacum = 0;
            for( Objetivo objetivo: ulms.getObjetivos()){
                poracum += (objetivo.getSuperado()/10);
                contacum += 1;
            }

            contacum = contacum * 10;
            pu.setValue((poracum*10)/contacum);
            pc.addUnidad(pu);
        }
        return pc;
    }

    @Override
    @Transactional
    public UsuarioLMS save(UsuarioLMS usuarioLMS) {
        return repository.save(usuarioLMS);
    }

    @Override
    @Transactional
    public Objetivo saveObjetivo(Objetivo objetivo) {
        return objetivoRepository.save(objetivo);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean compararListas(List<Objetivo> lista1,  List<Objetivo> lista2){
        if (lista1.size() != lista2.size()) {
            return false;
        }
        for (int i = 0; i < lista1.size(); i++) {
            if (!lista1.get(i).getNombre().equals(lista2.get(i).getNombre())){return false;}
        }
        return true;
    }

}
