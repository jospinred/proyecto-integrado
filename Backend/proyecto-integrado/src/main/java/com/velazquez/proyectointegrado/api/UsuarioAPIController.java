package com.velazquez.proyectointegrado.api;

import com.velazquez.proyectointegrado.api.dto.*;
import com.velazquez.proyectointegrado.mappers.Mapper;
import com.velazquez.proyectointegrado.model.*;
import com.velazquez.proyectointegrado.services.*;
import com.velazquez.proyectointegrado.services.Impl.JWTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
public class UsuarioAPIController {
    static final Logger logger = LoggerFactory.getLogger(UsuarioAPIController.class);
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private OfertanteService ofertanteService;
    @Autowired
    private ConsumidorService consumidorService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private ActividadService actividadService;
    @Autowired
    private JPAUserDetailsService jpaUserDetailsService;


    @Autowired
    private Mapper<Usuario, UsuarioDTO> usuarioMapper;
    @Autowired
    private Mapper<Ofertante, UsuarioDTO> ofertanteMapper;
    @Autowired
    private Mapper<Consumidor, UsuarioDTO> consumidorMapper;
    @Autowired
    private Mapper<Oferta, OfertaDTO> ofertaMapper;
    @Autowired
    private Mapper<Admin, UsuarioDTO> adminMapper;
    @Autowired
    private Mapper<Actividad, ActividadDTO> actividadMapper;

    @Autowired
    private JWTService jwtService;

    // Registrar un usuario
    @PostMapping(path = "/api/usuario", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioBD;
        logger.info("Create usuario: " + usuarioDTO.toString());
        String passaux = usuarioDTO.getPassword();
        usuarioDTO.setPassword(new BCryptPasswordEncoder(15).encode(passaux));

        Usuario usuario = usuarioMapper.mapFrom(usuarioDTO);
        usuarioBD = usuarioService.findUsuarioByUsername(usuario.getUsername());
        if (usuarioDTO.getRol().equalsIgnoreCase("ofertante")) {
            if (usuarioBD.isPresent()) {
                ofertanteService.insertOfertanteId(usuarioBD.get().getId());
            } else {
                Ofertante ofertante = ofertanteMapper.mapFrom(usuarioDTO);
                ofertanteService.insertOfertante(ofertante);
            }
        } else if (usuarioDTO.getRol().equalsIgnoreCase("consumidor")) {
            if (usuarioBD.isPresent()) {
                consumidorService.insertConsumidorId(usuarioBD.get().getId());
            } else {
                Consumidor consumidor = consumidorMapper.mapFrom(usuarioDTO);
                consumidorService.insertConsumidor(consumidor);
            }
        } else if (usuarioDTO.getRol().equalsIgnoreCase("administrador")) {
            if (usuarioBD.isPresent()) {
                adminService.insertAdminId(usuarioBD.get().getId());
            } else {
                Admin admin = adminMapper.mapFrom(usuarioDTO);
                adminService.insertAdmin(admin);
            }
        }
        logger.info("Saved usuario: " + usuario);
        return new ResponseEntity<>(usuarioMapper.mapTo(usuario), HttpStatus.CREATED);
    }

    //Validad JWT
    @PostMapping("/api/usuario/validarJWT")
    public boolean validateToken(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        String username = requestBody.get("username");
        if (username == null || username.isEmpty()) {
            return false;
        }
        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(username);
        return jwtService.isTokenValid(token, userDetails);
    }

    // Iniciar sesion con un usuario
    @PostMapping("/api/usuario/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
        // Autenticar al usuario
        Optional<Usuario> user = usuarioService.login(loginRequest.getUsername(), loginRequest.getPassword());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
        Usuario usuario = user.get();
        int permisos = 0;
        int consumidor = consumidorService.selectById(usuario.getId());
        int ofertante = ofertanteService.selectById(usuario.getId());
        int admin = adminService.selectById(usuario.getId());
        System.out.println("Es consumidor? " + consumidor);
        System.out.println("Es ofertante? " + ofertante);
        System.out.println("Es admin? " + admin);
        if (consumidor > 0) {
            permisos += 1;
        }
        if (ofertante > 0) {
            permisos += 3;
        }
        if (admin > 0) {
            permisos += 5;
        }
        // Generar token JWT
        String token = jwtService.generateToken((UserDetails) usuario);
        // Devolver el token en la respuesta
        return ResponseEntity.ok(new JwtResponse(token, permisos, user.get().getUsername(), user.get().getId()));
    }

    // Obtener un usuario por su id
    @GetMapping(path = "/api/usuario/{id}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable Long id) {
        logger.info("Get usuario by id: " + id + ")");
        Optional<Usuario> usuario = usuarioService.findUsuarioById(id);
        if (usuario.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(usuarioMapper.mapTo(usuario.get()), HttpStatus.OK);
    }

    // Listar todos los usuarios
    @GetMapping(path = "/api/usuario")
    public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
        logger.info("Get all usuarios");
        List<Usuario> usuarios = usuarioService.getUsuarios();
        if (usuarios.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for (Usuario usuario: usuarios){
            usuariosDTO.add(usuarioMapper.mapTo(usuario));
        }

        return new ResponseEntity<>(usuariosDTO, HttpStatus.OK);
    }

    // Actualizar un usuario
    @PutMapping(path = "/api/usuario")
    public ResponseEntity<Object> updateUsuario(@RequestBody UpdatePassDTO usuarioPass) {
        logger.info("Update usuario con id: " + usuarioPass.getId());
        HashMap<String, String> mapa = new HashMap<>();
        Optional<Usuario> usuarioOpcional = usuarioService.findUsuarioById(usuarioPass.getId());
        if (usuarioOpcional.isEmpty()) {
            mapa.put("Resultado", "El usuario no se encuentra en la base de datos");
            return new ResponseEntity<>(mapa, HttpStatus.NOT_FOUND);
        }
        Usuario usuario = usuarioOpcional.get();
        usuario.setPassword(new BCryptPasswordEncoder(15).encode(usuarioPass.getPassword()));
        Usuario usuarioUpdated = usuarioService.updateUsuario(usuario);
        return new ResponseEntity<>(usuarioMapper.mapTo(usuarioUpdated), HttpStatus.OK);
    }

    // Borrar un usuario por su id
    @DeleteMapping(path = "/api/usuario/{id}")
    public ResponseEntity<Object> deleteUsuario(@PathVariable Long id) {
        logger.info("Delete usuario con id: " + id);
        HashMap<String, String> mapa = new HashMap<>();
        if (id == null) {
            mapa.put("Resultado", "El usuario no se encuentra en la base de datos");
            return new ResponseEntity<>(mapa, HttpStatus.NOT_FOUND);
        }
        usuarioService.deleteUsuario(id);
        mapa.put("Resultado", "Usuario borrado correctamente");
        return new ResponseEntity<>(mapa, HttpStatus.OK);
    }

    // Borrar un usuario por su id
    @DeleteMapping(path = "/api/usuario-username/{username}")
    public ResponseEntity<Object> deleteUsuarioUsername(@PathVariable String username) {
        logger.info("Delete usuario con username: " + username);
        HashMap<String, String> mapa = new HashMap<>();
        if (username == null) {
            mapa.put("Resultado", "Nombre inválido");
            return new ResponseEntity<>(mapa, HttpStatus.NOT_FOUND);
        }
        Optional<Usuario> usuario = usuarioService.findUsuarioByUsername(username);
        if (usuario == null) {
            mapa.put("Resultado", "Usuario no encontrado en la base de datos");
            return new ResponseEntity<>(mapa, HttpStatus.NOT_FOUND);
        }
        usuarioService.deleteUsuario(usuario.get().getId());
        mapa.put("Resultado", "Usuario borrado correctamente");
        return new ResponseEntity<>(mapa, HttpStatus.OK);
    }

    // Listar preferencias de usuario
    @GetMapping(path = "/api/usuarioPreferencias/{id}")
    public ResponseEntity<Object> getPreferenciasUsuario(@PathVariable Long id) {
        logger.info("Get preferencias usuario con id: " + id);
        Optional<Consumidor> consumidorOpcional = consumidorService.findUsuarioById(id);
        HashMap<String, String> mapa = new HashMap<>();
        if (consumidorOpcional.isEmpty()) {
            mapa.put("Respuesta", "El usuario no se encuentra en la base de datos");
            return new ResponseEntity<>(mapa, HttpStatus.NOT_FOUND);
        }
        Set<Actividad> listaPreferencias = consumidorOpcional.get().getActividadesPreferencia();
        ArrayList<Object> listaSalida = new ArrayList<>();
        for (Actividad actividad : listaPreferencias) {
            listaSalida.add(actividadMapper.mapTo(actividad));
        }
        return new ResponseEntity<>(listaSalida, HttpStatus.OK);
    }

    // Listar ofertas apuntado de usuario
    @GetMapping(path = "/api/ofertasapuntado/{id}")
    public ResponseEntity<Object> getOfertasApuntadoUsuario(@PathVariable Long id) {
        logger.info("Get ofertas apuntado del usuario con id: " + id);
        Optional<Consumidor> consumidorOpcional = consumidorService.findUsuarioById(id);
        HashMap<String, String> mapa = new HashMap<>();
        if (consumidorOpcional.isEmpty()) {
            mapa.put("Respuesta", "El usuario no se encuentra en la base de datos");
            return new ResponseEntity<>(mapa, HttpStatus.NOT_FOUND);
        }
        Set<Oferta> listaOfertasApuntado = consumidorOpcional.get().getActividadesApuntado();
        ArrayList<Object> listaSalida = new ArrayList<>();
        for (Oferta oferta : listaOfertasApuntado) {
            listaSalida.add(ofertaMapper.mapTo(oferta));
        }
        return new ResponseEntity<>(listaSalida, HttpStatus.OK);
    }

    // Actualizar preferencias de usuario
    @PutMapping(path = "/api/usuarioPreferencias/{idUsuario}")
    public ResponseEntity<Object> updatePreferenciasUsuario(@PathVariable Long idUsuario, @RequestBody PreferenciaDTO preferencia) {
        logger.info("Update preferencias usuario con id: " + idUsuario);
        Optional<Consumidor> consumidorOpcional = consumidorService.findUsuarioById(idUsuario);
        HashMap<String, String> mapa = new HashMap<>();
        if (consumidorOpcional.isEmpty()) {
            mapa.put("Respuesta", "El usuario no se encuentra en la base de datos");
            return new ResponseEntity<>(mapa, HttpStatus.NOT_FOUND);
        }
        Consumidor consumidorBD = consumidorOpcional.get();
        Optional<Actividad> actividad = actividadService.findActividadById(preferencia.getId());
        if (preferencia.getOperacion().equals("add")) {
            consumidorBD.setActividadesPreferencia(actividad.get());
        } else {
            Actividad preferenciaBorrar = consumidorBD.getActividadesPreferencia().stream()
                    .filter(act -> act.getId().equals(actividad.get().getId()))
                    .findFirst()
                    .orElse(null);
            consumidorBD.delActividadesPreferencia(preferenciaBorrar);
        }
        consumidorService.updateConsumidor(consumidorBD);
        mapa.put("Resultado", "Update preferencias correctamente");
        return new ResponseEntity<>(mapa, HttpStatus.OK);
    }

    // Actualizar cualificaciones de usuario
    @PutMapping(path = "/api/usuarioCualificaciones/{idUsuario}")
    public ResponseEntity<Object> updateCualificacionesUsuario(@PathVariable Long idUsuario, @RequestBody PreferenciaDTO cualificacion) {
        logger.info("Update cualificaciones usuario con id: " + idUsuario);
        Optional<Ofertante> ofertanteOptional = ofertanteService.findOfertanteById(idUsuario);
        HashMap<String, String> mapa = new HashMap<>();
        if (ofertanteOptional.isEmpty()) {
            mapa.put("Respuesta", "El usuario no se encuentra en la base de datos");
            return new ResponseEntity<>(mapa, HttpStatus.NOT_FOUND);
        }
        Ofertante ofertanteBD = ofertanteOptional.get();
        Optional<Actividad> actividad = actividadService.findActividadById(cualificacion.getId());
        if (cualificacion.getOperacion().equals("add")) {
            ofertanteBD.setActividadCualificado(actividad.get());
        } else {
            Actividad preferenciaBorrar = ofertanteBD.getActividadesCualificado().stream()
                    .filter(act -> act.getId().equals(actividad.get().getId()))
                    .findFirst()
                    .orElse(null);
            ofertanteBD.delActividadCualificado(preferenciaBorrar);
        }
        ofertanteService.updateOfertante(ofertanteBD);
        mapa.put("Resultado", "Update cualificaciones correctamente");
        return new ResponseEntity<>(mapa, HttpStatus.OK);
    }

    // Listar cualificaciones de usuario
    @GetMapping(path = "/api/usuarioCualificaciones/{id}")
    public ResponseEntity<Object> getCualificacionesUsuario(@PathVariable Long id) {
        logger.info("Get cualificaciones usuario con id: " + id);
        Optional<Ofertante> ofertanteOpcional = ofertanteService.findOfertanteById(id);
        HashMap<String, String> mapa = new HashMap<>();
        if (ofertanteOpcional.isEmpty()) {
            mapa.put("Respuesta", "El usuario no se encuentra en la base de datos");
            return new ResponseEntity<>(mapa, HttpStatus.NOT_FOUND);
        }
        Set<Actividad> listaCualificaciones = ofertanteOpcional.get().getActividadesCualificado();
        ArrayList<Object> listaSalida = new ArrayList<>();
        for (Actividad actividad : listaCualificaciones) {
            listaSalida.add(actividadMapper.mapTo(actividad));
        }
        return new ResponseEntity<>(listaSalida, HttpStatus.OK);
    }
}
