package com.velazquez.proyectointegrado.api;

import com.velazquez.proyectointegrado.api.dto.DemandaDTO;
import com.velazquez.proyectointegrado.api.dto.UpdatePassDTO;
import com.velazquez.proyectointegrado.mappers.Mapper;
import com.velazquez.proyectointegrado.model.*;
import com.velazquez.proyectointegrado.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin
@RestController
public class DemandaAPIController {
    static final Logger logger = LoggerFactory.getLogger(DemandaAPIController.class);

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ConsumidorService consumidorService;
    @Autowired
    private DemandaService demandaService;
    @Autowired
    private ActividadService actividadService;

    @Autowired
    private Mapper<Demanda, DemandaDTO> demandaMapper;

    // Añadir una demanda
    @PostMapping(path = "/api/demanda", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DemandaDTO> addDemanda(@RequestBody DemandaDTO demandaDTO) {
        logger.info("Add demanda: " + demandaDTO.toString());
        Demanda demanda = demandaMapper.mapFrom(demandaDTO);
        if(demanda!=null){
            String idActividadString = demandaDTO.getActividad();
            Optional<Actividad> actividadOpcional = actividadService.findActividadById(Long.parseLong(idActividadString));
            Actividad actividad = actividadOpcional.get();
            Optional<Usuario> usuarioDemandante = usuarioService.findUsuarioByUsername(demandaDTO.getCreadorDemanda());
            Optional<Consumidor> consumidorOpcional = consumidorService.findUsuarioById(usuarioDemandante.get().getId());
            Consumidor consumidor = consumidorOpcional.get();
            demanda.setCreadorDemanda(consumidor);
            demanda.setActividad(actividad);
            demandaService.insertDemanda(demanda);
        }
        logger.info("Saved demanda: " + demanda);
        return new ResponseEntity<>(demandaMapper.mapTo(demanda), HttpStatus.CREATED);
    }

    //Obtener todas las demandas
    @GetMapping(path = "/api/demandas")
    public ResponseEntity<List<DemandaDTO>> getDemandas() {
        logger.info("Get demandas");
        List<Demanda> demandas = demandaService.getDemandas();
        List<DemandaDTO> demandasDTO = new ArrayList<>();
        demandas.forEach(demanda -> demandasDTO.add(demandaMapper.mapTo(demanda)));
        demandasDTO.forEach(demanda -> demanda.setActividad(extraerNombre(demanda.getActividad())));
        demandasDTO.forEach(demanda -> demanda.setCreadorDemanda(extraerUsername(demanda.getCreadorDemanda())));
        return new ResponseEntity<>(demandasDTO, HttpStatus.OK);
    }
    //Obtener todas las demandas de un usuario
    @GetMapping(path = "/api/demandas/{id}")
    public ResponseEntity<List<DemandaDTO>> getDemandasUsuario(@PathVariable Long id) {
        logger.info("Get demandas de un usuario");
        List<Demanda> demandas = demandaService.getDemandasUsuario(id);
        List<DemandaDTO> demandasDTO = new ArrayList<>();
        demandas.forEach(demanda -> demandasDTO.add(demandaMapper.mapTo(demanda)));
        demandasDTO.forEach(demanda -> demanda.setActividad(extraerNombre(demanda.getActividad())));
        demandasDTO.forEach(demanda -> demanda.setCreadorDemanda(extraerUsername(demanda.getCreadorDemanda())));
        return new ResponseEntity<>(demandasDTO, HttpStatus.OK);
    }


    public static String extraerNombre(String input) {
        // Define la expresión regular para encontrar el valor del campo nombre
        String regex = "nombre='([^']*)'";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // Verifica si encuentra un match y devuelve el grupo capturado
        if (matcher.find()) {
            return matcher.group(1);
        }

        // Si no encuentra el patrón, devuelve null o un valor por defecto
        return null;
    }
    public static String extraerUsername(String input) {
        // Define la expresión regular para encontrar el valor del campo nombre
        String regex = "username='([^']*)'";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // Verifica si encuentra un match y devuelve el grupo capturado
        if (matcher.find()) {
            return matcher.group(1);
        }

        // Si no encuentra el patrón, devuelve null o un valor por defecto
        return null;
    }


    // Obtener una demanda por su id
    @GetMapping(path = "/api/demanda/{id}")
    public ResponseEntity<DemandaDTO> getDemanda(@PathVariable Long id) {
        logger.info("Get demanda by id: " + id + ")");
        Optional<Demanda> demanda = demandaService.findDemandaById(id);
        if (demanda.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(demandaMapper.mapTo(demanda.get()), HttpStatus.OK);
    }

    // Borrar una demanda por su id
    @DeleteMapping(path = "/api/demanda/{id}")
    public ResponseEntity<Object> deleteDemanda(@PathVariable Long id) {
        logger.info("Delete demanda by id: " + id + ")");
        HashMap<String, String> mapa = new HashMap<>();
        demandaService.deleteDemanda(id);
        mapa.put("Resultado", "Demanda borrada correctamente");
        return new ResponseEntity<>(mapa, HttpStatus.OK);
    }

    // Actualizar una demanda
    @PutMapping(path = "/api/demanda")
    public ResponseEntity<Object> updateDemanda(@RequestBody DemandaDTO demandaDTO) {
        logger.info("Update demanda by id: " + demandaDTO.getId());
        Optional<Demanda> demandaOpcionalBD = demandaService.findDemandaById(demandaDTO.getId());
        Demanda demandaBD = demandaOpcionalBD.get();
        demandaBD.setFecha(demandaDTO.getFecha());
        demandaBD.setLocalidad(demandaDTO.getLocalidad());
        Long idActividad = Long.parseLong(demandaDTO.getActividad());
        Optional<Actividad> actividad = actividadService.findActividadById(idActividad);
        demandaBD.setActividad(actividad.get());
        demandaService.updateDemanda(demandaBD);
        return new ResponseEntity<>(demandaMapper.mapTo(demandaBD), HttpStatus.OK);
    }
}
