package com.cpcd.microservices.app.objetivos.microservicioobjetivos.controller;


import com.cpcd.microservices.app.objetivos.microservicioobjetivos.models.entity.*;
import com.cpcd.microservices.app.objetivos.microservicioobjetivos.services.ServicioObjetivo;
import com.cpcd.microservices.app.servicescommons.token.ModelToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class ControllerObjetivo {
    private Logger log = LoggerFactory.getLogger(ControllerObjetivo.class);

    @Autowired
    private ServicioObjetivo servicioObjetivo;

    @GetMapping
    public ResponseEntity<?> listar(){
        return ResponseEntity.ok().body(servicioObjetivo.findAll());
    }

    @GetMapping("/objetivoscurso")
    public ResponseEntity<?> getPorcentajeObjetivos(@RequestHeader("Authorization") String token){
        ModelToken jwtService = new ModelToken(token);

        Long userid = jwtService.getUserId();
        Long curso =  jwtService.getCurso();
        Long actividad =  jwtService.getActividad();
        String dominio = (String) jwtService.getDominio();

        PorcentajeCurso pocu = servicioObjetivo.findByUseridCursoActividadDominio(userid,curso,actividad,dominio);
        return ResponseEntity.ok(pocu);

    }

    @GetMapping("/objetivos")
    public ResponseEntity<?> getUsuarioLMS(@RequestHeader("Authorization") String token){
        ModelToken jwtService = new ModelToken(token);
        Long userid = jwtService.getUserId();
        Long curso =  jwtService.getCurso();
        Long actividad =  jwtService.getActividad();
        String dominio = (String) jwtService.getDominio();
        String idunidad = (String) jwtService.getIdUnidad();

        Optional<UsuarioLMS> ulms = servicioObjetivo.findByUseridCursoActividadDominioIdunidad(userid,curso,actividad,dominio,idunidad);
        if (ulms.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ulms.get());

    }

    @PostMapping("/evaluacion")
    public ResponseEntity<?> addEvaluacion(@RequestHeader("Authorization") String token, @RequestBody Evaluacion evaluacion){
        ModelToken jwtService = new ModelToken(token);
        Long userid = jwtService.getUserId();
        Long curso =  jwtService.getCurso();
        Long actividad =  jwtService.getActividad();
        String dominio = (String) jwtService.getDominio();
        String idunidad = (String) jwtService.getIdUnidad();


        Optional<UsuarioLMS> ulms = servicioObjetivo.findByUseridCursoActividadDominioIdunidad(userid,curso,actividad,dominio,idunidad);
        if (ulms.isEmpty()) {
            UsuarioLMS usuarioLMS = new UsuarioLMS(userid, curso, actividad, dominio, idunidad);
            usuarioLMS.setEvaluacion(evaluacion);
            UsuarioLMS usuarioLMS1 = servicioObjetivo.save(usuarioLMS);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioLMS1);
        }else{
            return ResponseEntity.ok(ulms.get());
        }
    }

    @PutMapping("/setevaluacion")
    public ResponseEntity<?> setEvaluacion(@RequestHeader("Authorization") String token, @RequestBody Evaluacion evaluacion){
        ModelToken jwtService = new ModelToken(token);
        Long userid = jwtService.getUserId();
        Long curso =  jwtService.getCurso();
        Long actividad =  jwtService.getActividad();
        String dominio = (String) jwtService.getDominio();
        String idunidad = (String) jwtService.getIdUnidad();

        Optional<UsuarioLMS> ulms = servicioObjetivo.findByUseridCursoActividadDominioIdunidad(userid,curso,actividad,dominio,idunidad);
        if (ulms.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        UsuarioLMS usuarioLMS = ulms.get();
        if (usuarioLMS.getEvaluacion()!= null){
            Evaluacion eval = usuarioLMS.getEvaluacion();
            if (eval.getNota()==-1){
                eval.setNota(evaluacion.getNota());
                usuarioLMS = servicioObjetivo.save(usuarioLMS);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioLMS);
        }else{
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/objetivos")
    public ResponseEntity<?> addUsuarioLMS(@RequestHeader("Authorization") String token, @RequestBody List<Objetivo> objetivos){
        ModelToken jwtService = new ModelToken(token);
        Long userid = jwtService.getUserId();
        Long curso =  jwtService.getCurso();
        Long actividad =  jwtService.getActividad();
        String dominio = (String) jwtService.getDominio();
        String idunidad = (String) jwtService.getIdUnidad();


        Optional<UsuarioLMS> ulms = servicioObjetivo.findByUseridCursoActividadDominioIdunidad(userid,curso,actividad,dominio,idunidad);
        if (ulms.isEmpty()) {
            UsuarioLMS usuarioLMS = new UsuarioLMS(userid, curso, actividad, dominio, idunidad);
            objetivos.forEach(obj -> {
                usuarioLMS.addObjetivo(obj);
            });
            UsuarioLMS usuarioLMS1 = servicioObjetivo.save(usuarioLMS);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioLMS1);
        }else{
            UsuarioLMS usuarioLMSPersistent = ulms.get();

            if (servicioObjetivo.compararListas(usuarioLMSPersistent.getObjetivos(),objetivos)){
                return ResponseEntity.ok(ulms.get());
            }else {
                usuarioLMSPersistent.setObjetivos(objetivos);
                return ResponseEntity.status(HttpStatus.CREATED).body(servicioObjetivo.save(usuarioLMSPersistent));
            }
        }
    }

    @PostMapping
    public ResponseEntity<?> addUsuariov0LMS(@RequestHeader("Authorization") String token, @RequestBody List<Objetivo> objetivos){
        ModelToken jwtService = new ModelToken(token);
        Long userid = jwtService.getUserId();
        Long curso =  jwtService.getCurso();
        Long actividad =  jwtService.getActividad();
        String dominio = (String) jwtService.getDominio();
        String idunidad = (String) jwtService.getIdUnidad();


        UsuarioLMS usuarioLMS = new UsuarioLMS(userid, curso, actividad, dominio, idunidad);
        objetivos.forEach(obj -> {
            usuarioLMS.addObjetivo(obj);
        });
        UsuarioLMS usuarioLMS1 = servicioObjetivo.save(usuarioLMS);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioLMS1);
    }

    @PutMapping
    public ResponseEntity<?> setObjetivoCompleto(@RequestHeader("Authorization") String token, @RequestBody Objetivo objetivo){
        ModelToken jwtService = new ModelToken(token);
        Long userid = jwtService.getUserId();
        Long curso =  jwtService.getCurso();
        Long actividad =  jwtService.getActividad();
        String dominio = (String) jwtService.getDominio();
        String idunidad = (String) jwtService.getIdUnidad();

        Optional<UsuarioLMS> ulms = servicioObjetivo.findByUseridCursoActividadDominioIdunidad(userid,curso,actividad,dominio,idunidad);
        if (ulms.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        UsuarioLMS usuarioLMS = ulms.get();
        if (usuarioLMS.getObjetivos().contains(objetivo)){
            Objetivo obj = usuarioLMS.getObjetivos().get(usuarioLMS.getObjetivos().indexOf(objetivo));
            if ((obj.getSuperado() == 0)&&(!obj.getNombre().contains("nota"))) {
                obj.setSuperado(100);
                usuarioLMS = servicioObjetivo.save(usuarioLMS);
            }else if (obj.getSuperado() == -1) {
                obj.setSuperado(objetivo.getSuperado());
                usuarioLMS = servicioObjetivo.save(usuarioLMS);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioLMS);
        }else{
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
    }
}
