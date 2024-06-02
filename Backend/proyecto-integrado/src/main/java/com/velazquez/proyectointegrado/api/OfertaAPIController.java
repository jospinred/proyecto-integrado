package com.velazquez.proyectointegrado.api;

import com.velazquez.proyectointegrado.api.dto.DemandaDTO;
import com.velazquez.proyectointegrado.api.dto.OfertaDTO;
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
public class OfertaAPIController {
    static final Logger logger = LoggerFactory.getLogger(OfertaAPIController.class);

    @Autowired
    private OfertaService ofertaService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private OfertanteService ofertanteService;
    @Autowired
    private ConsumidorService consumidorService;
    @Autowired
    private ActividadService actividadService;

    @Autowired
    private Mapper<Oferta, OfertaDTO> ofertaMapper;

    // Añadir una oferta
    @PostMapping(path = "/api/oferta", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OfertaDTO> addOferta(@RequestBody OfertaDTO ofertaDTO) {
        logger.info("Add oferta: " + ofertaDTO.toString());
        Oferta oferta = ofertaMapper.mapFrom(ofertaDTO);
        if(oferta!=null){
            String idActividadString = ofertaDTO.getActividad();
            Optional<Actividad> actividadOpcional = actividadService.findActividadById(Long.parseLong(idActividadString));
            Actividad actividad = actividadOpcional.get();
            System.out.println("creador de la oferta: "+ofertaDTO.getCreadorOferta());//AQUI
            Optional<Ofertante> ofertanteOpcional = ofertanteService.findOfertanteByUsername(ofertaDTO.getCreadorOferta());
            System.out.println("ofertanteOpcional"+ofertanteOpcional);
            Ofertante ofertante = ofertanteOpcional.get();
            oferta.setCreadorOferta(ofertante);
            oferta.setParticipantes(0);
            oferta.setActividad(actividad);
            ofertaService.insertOferta(oferta);
        }
        logger.info("Saved oferta: " + oferta);
        return new ResponseEntity<>(ofertaMapper.mapTo(oferta), HttpStatus.CREATED);
    }

    //Obtener todas las ofertas
    @GetMapping(path = "/api/ofertas")
    public ResponseEntity<List<OfertaDTO>> getOfertas() {
        logger.info("Get ofertas");
        List<Oferta> ofertas = ofertaService.getOfertas();
        List<OfertaDTO> ofertasDTO = new ArrayList<>();
        ofertas.forEach(oferta -> ofertasDTO.add(ofertaMapper.mapTo(oferta)));
        ofertasDTO.forEach(oferta -> oferta.setActividad(extraerNombre(oferta.getActividad())));
        ofertasDTO.forEach(oferta -> oferta.setCreadorOferta(extraerUsername(oferta.getCreadorOferta())));
        return new ResponseEntity<>(ofertasDTO, HttpStatus.OK);
    }

    //Obtener todas las ofertas de un usuario
    @GetMapping(path = "/api/ofertas/{id}")
    public ResponseEntity<List<OfertaDTO>> getOfertasUsuario(@PathVariable Long id) {
        logger.info("Get ofertas de un usuario");
        List<Oferta> ofertas = ofertaService.getOfertasUsuario(id);
        List<OfertaDTO> ofertasDTO = new ArrayList<>();
        ofertas.forEach(oferta -> ofertasDTO.add(ofertaMapper.mapTo(oferta)));
        ofertasDTO.forEach(oferta -> oferta.setActividad(extraerNombre(oferta.getActividad())));
        ofertasDTO.forEach(oferta -> oferta.setCreadorOferta(extraerUsername(oferta.getCreadorOferta())));
        return new ResponseEntity<>(ofertasDTO, HttpStatus.OK);
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


    // Obtener una oferta por su id
    @GetMapping(path = "/api/oferta/{id}")
    public ResponseEntity<OfertaDTO> getOferta(@PathVariable Long id) {
        logger.info("Get oferta by id: " + id + ")");
        Optional<Oferta> oferta = ofertaService.findOfertaById(id);
        if (oferta.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ofertaMapper.mapTo(oferta.get()), HttpStatus.OK);
    }
    // Apuntarse a una oferta
    @PostMapping(path = "/api/oferta/apuntarse/{idOferta}/{userUsuario}")
    public ResponseEntity<Object> apuntarseOferta(@PathVariable Long idOferta, @PathVariable String userUsuario) {
        logger.info("Usuario con id: "+userUsuario+ " se apunta a oferta con id: " + idOferta );
        HashMap<String,String> mapa = new HashMap<>();
        Optional<Oferta> ofertaOpcional = ofertaService.findOfertaById(idOferta);
        Optional<Consumidor> consumidorOpcional = consumidorService.findConsumidorByUsername(userUsuario);
        if (ofertaOpcional.isEmpty() || consumidorOpcional.isEmpty()) {
            System.out.println(userUsuario);
            System.out.println(consumidorOpcional);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Oferta oferta = ofertaOpcional.get();
        Consumidor consumidor = consumidorOpcional.get();
        if(consumidorService.comprobarApuntadoOferta(consumidor,oferta)){
            mapa.put("Resultado", "El usuario ya se encuentra apuntado a esta actividad");
            return new ResponseEntity<>(mapa, HttpStatus.OK);
        }

        oferta.setParticipantes(oferta.getParticipantes()+1);
        consumidor.setActividadesApuntado(oferta);
        consumidorService.updateConsumidor(consumidor);
        ofertaService.updateOferta(oferta);
        mapa.put("Resultado", "OK");
        return new ResponseEntity<>(mapa, HttpStatus.OK);
    }

    // Darse de baja de una oferta
    @PostMapping(path = "/api/oferta/desapuntarse/{idOferta}/{userUsuario}")
    public ResponseEntity<Object> desapuntarseOferta(@PathVariable Long idOferta, @PathVariable String userUsuario) {
        logger.info("Usuario con id: "+userUsuario+ " se desapunta a oferta con id: " + idOferta );
        HashMap<String,String> mapa = new HashMap<>();
        Optional<Oferta> ofertaOpcional = ofertaService.findOfertaById(idOferta);
        Optional<Consumidor> consumidorOpcional = consumidorService.findConsumidorByUsername(userUsuario);
        if (ofertaOpcional.isEmpty() || consumidorOpcional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Oferta oferta = ofertaOpcional.get();
        Consumidor consumidor = consumidorOpcional.get();
        if(consumidorService.comprobarApuntadoOferta(consumidor,oferta)){
            oferta.setParticipantes(oferta.getParticipantes()-1);
            consumidor.delActividadesApuntado(oferta);
        }
        consumidorService.updateConsumidor(consumidor);
        ofertaService.updateOferta(oferta);
        mapa.put("Resultado", "OK");
        return new ResponseEntity<>(mapa, HttpStatus.OK);
    }

    // Borrar una oferta por su id
    @DeleteMapping(path = "/api/oferta/{id}")
    public ResponseEntity<Object> deleteOferta(@PathVariable Long id) {
        logger.info("Delete oferta by id: " + id + ")");
        HashMap<String, String> mapa = new HashMap<>();
        ofertaService.deleteOferta(id);
        mapa.put("Resultado", "Oferta borrada correctamente");
        return new ResponseEntity<>(mapa, HttpStatus.OK);
    }

    // Actualizar una oferta
    @PutMapping(path = "/api/oferta")
    public ResponseEntity<Object> updateOferta(@RequestBody OfertaDTO ofertaDTO) {
        logger.info("Update oferta by id: " + ofertaDTO.getId());
        Optional<Oferta> ofertaOptionalBD = ofertaService.findOfertaById(ofertaDTO.getId());
        Oferta ofertaBD = ofertaOptionalBD.get();
        ofertaBD.setDescripcion(ofertaDTO.getDescripcion());
        ofertaBD.setDuracion(ofertaDTO.getDuracion());
        ofertaBD.setFecha(ofertaDTO.getFecha());
        ofertaBD.setLocalidad(ofertaDTO.getLocalidad());
        Long idActividad = Long.parseLong(ofertaDTO.getActividad());
        Optional<Actividad> actividad = actividadService.findActividadById(idActividad);
        ofertaBD.setActividad(actividad.get());
        ofertaBD.setMaterialNecesario(ofertaDTO.getMaterialNecesario());
        ofertaBD.setMaterialOfertado(ofertaDTO.getMaterialOfertado());
        ofertaBD.setMinimoPlazas(ofertaDTO.getMinimoPlazas());
        ofertaBD.setMaximoPlazas(ofertaDTO.getMaximoPlazas());
        ofertaBD.setPreparacionNecesaria(ofertaDTO.getPreparacionNecesaria());
        ofertaBD.setTarifa(ofertaDTO.getTarifa());
        ofertaService.updateOferta(ofertaBD);
        return new ResponseEntity<>(ofertaMapper.mapTo(ofertaBD), HttpStatus.OK);
    }

    //Obtener usuarios apuntados a una oferta
    @GetMapping(path = "/api/usuariosapuntadosoferta/{idOferta}")
    public ResponseEntity<Object> getUsuariosApuntadosOferta(@PathVariable Long idOferta) {
        logger.info("Get usuarios apuntados a la oferta con id: " + idOferta);
        Optional<Oferta> oferta = ofertaService.findOfertaById(idOferta);
        if (oferta.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        HashMap<String, String> mapa = new HashMap<>();
        ArrayList<Object> listaSalida = new ArrayList<>();
        Set<Consumidor> consumidoresApuntados = oferta.get().getConsumidoresApuntados();
        for (Consumidor consumidor : consumidoresApuntados){
            mapa.put("id", consumidor.getId().toString());
            mapa.put("username", consumidor.getUsername());
            listaSalida.add(mapa);
        }
        return new ResponseEntity<>(listaSalida, HttpStatus.OK);
    }
}
