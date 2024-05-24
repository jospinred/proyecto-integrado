package com.velazquez.proyectointegrado.api;

import com.velazquez.proyectointegrado.api.dto.JwtResponse;
import com.velazquez.proyectointegrado.api.dto.LoginDTO;
import com.velazquez.proyectointegrado.api.dto.UsuarioDTO;
import com.velazquez.proyectointegrado.mappers.Mapper;
import com.velazquez.proyectointegrado.model.Admin;
import com.velazquez.proyectointegrado.model.Consumidor;
import com.velazquez.proyectointegrado.model.Ofertante;
import com.velazquez.proyectointegrado.model.Usuario;
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

import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
public class UsuarioAPIController {
    static final Logger logger = LoggerFactory.getLogger(UsuarioAPIController.class);
    @Autowired private UsuarioService usuarioService;
    @Autowired private OfertanteService ofertanteService;
    @Autowired private ConsumidorService consumidorService;
    @Autowired private AdminService adminService;
    @Autowired private JPAUserDetailsService jpaUserDetailsService;


    @Autowired private Mapper<Usuario, UsuarioDTO> usuarioMapper;
    @Autowired private Mapper<Ofertante, UsuarioDTO> ofertanteMapper;
    @Autowired private Mapper<Consumidor, UsuarioDTO> consumidorMapper;
    @Autowired private Mapper<Admin, UsuarioDTO> adminMapper;

    @Autowired private JWTService jwtService;

    // Registrar un usuario
    @PostMapping(path = "/api/usuario", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UsuarioDTO> createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuarioBD;
        logger.info("Create usuario: " + usuarioDTO.toString());
        String passaux = usuarioDTO.getPassword();
        usuarioDTO.setPassword(new BCryptPasswordEncoder(15).encode(passaux));

        Usuario usuario = usuarioMapper.mapFrom(usuarioDTO);
        usuarioBD = usuarioService.findUsuarioByUsername(usuario.getUsername());
        if(usuarioDTO.getRol().equalsIgnoreCase("ofertante")){
            if(usuarioBD.isPresent()){
                ofertanteService.insertOfertanteId(usuarioBD.get().getId());
            } else {
                Ofertante ofertante = ofertanteMapper.mapFrom(usuarioDTO);
                ofertanteService.insertOfertante(ofertante);
            }
        } else if (usuarioDTO.getRol().equalsIgnoreCase("consumidor")){
            if(usuarioBD.isPresent()){
                consumidorService.insertConsumidorId(usuarioBD.get().getId());
            } else {
                Consumidor consumidor = consumidorMapper.mapFrom(usuarioDTO);
                consumidorService.insertConsumidor(consumidor);
            }
        } else if (usuarioDTO.getRol().equalsIgnoreCase("administrador")){
            if(usuarioBD.isPresent()){
                adminService.insertAdminId(usuarioBD.get().getId());
            } else {
                Admin admin = adminMapper.mapFrom(usuarioDTO);
                adminService.insertAdmin(admin);
            }
        }
        logger.info("Saved usuario: " + usuario);
        return new ResponseEntity<>(usuarioMapper.mapTo(usuario), HttpStatus.CREATED);
    }

    @PostMapping("/api/usuario/validarJWT")
    public boolean validateToken(@RequestBody Map<String, String> requestBody) {
        String token = requestBody.get("token");
        String username = requestBody.get("username");
        if(username == null || username.isEmpty()){
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
        Optional<Consumidor> consumidor = consumidorService.findUsuarioById(usuario.getId());
        Optional<Ofertante> ofertante = ofertanteService.findUsuarioById(usuario.getId());
        Optional<Admin> admin = adminService.findUsuarioById(usuario.getId());
        if(consumidor.isPresent()){
            permisos+=1;
        }
        if(ofertante.isPresent()){
            permisos+=3;
        }
        if(admin.isPresent()){
            permisos+=5;
        }
        // Generar token JWT
        String token = jwtService.generateToken((UserDetails) usuario);

        // Devolver el token en la respuesta
        return ResponseEntity.ok(new JwtResponse(token,permisos, user.get().getUsername()));
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
}
