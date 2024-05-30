package com.velazquez.proyectointegrado.api;

import com.velazquez.proyectointegrado.api.dto.ActividadDTO;
import com.velazquez.proyectointegrado.api.dto.OfertaDTO;
import com.velazquez.proyectointegrado.mappers.Mapper;
import com.velazquez.proyectointegrado.model.Actividad;
import com.velazquez.proyectointegrado.model.Oferta;
import com.velazquez.proyectointegrado.model.Ofertante;
import com.velazquez.proyectointegrado.model.Usuario;
import com.velazquez.proyectointegrado.services.ActividadService;
import com.velazquez.proyectointegrado.services.OfertaService;
import com.velazquez.proyectointegrado.services.OfertanteService;
import com.velazquez.proyectointegrado.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin
@RestController
public class ActividadAPIController {
    static final Logger logger = LoggerFactory.getLogger(ActividadAPIController.class);

    @Autowired
    private ActividadService actividadService;

    @Autowired
    private Mapper<Actividad, ActividadDTO> actividadMapper;

    //Obtener todas las actividades
    @GetMapping(path = "/api/actividades")
    public ResponseEntity<List<ActividadDTO>> getActividades() {
        logger.info("Get actividades");
        List<Actividad> actividades = actividadService.getActividades();
        System.out.println("Actividades normales: "+actividades);
        List<ActividadDTO> actividadesDTO = new ArrayList<>();
        actividades.forEach(actividad -> actividadesDTO.add(actividadMapper.mapTo(actividad)));
        System.out.println("Actividades dto: "+actividadesDTO);
        System.out.println("Actividades despues filtro: "+ actividadesDTO);
        return new ResponseEntity<>(actividadesDTO, HttpStatus.OK);
    }

    // Obtener una actividad por su id
    @GetMapping(path = "/api/actividad/{id}")
    public ResponseEntity<ActividadDTO> getActividad(@PathVariable Long id) {
        logger.info("Get actividad by id: " + id + ")");
        Optional<Actividad> actividad = actividadService.findActividadById(id);
        if (actividad.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(actividadMapper.mapTo(actividad.get()), HttpStatus.OK);
    }

    // Obtener una actividad por su nombre
    @GetMapping(path = "/api/actividadN/{nombre}")
    public ResponseEntity<ActividadDTO> getActividadNombre(@PathVariable String nombre) {
        logger.info("Get actividad by nombre: " + nombre);
        Optional<Actividad> actividad = actividadService.findActividadByNombre(nombre);
        if (actividad.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(actividadMapper.mapTo(actividad.get()), HttpStatus.OK);
    }
}
