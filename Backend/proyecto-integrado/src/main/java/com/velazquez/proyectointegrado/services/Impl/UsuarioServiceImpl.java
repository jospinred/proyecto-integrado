package com.velazquez.proyectointegrado.services.Impl;

import com.velazquez.proyectointegrado.model.Usuario;
import com.velazquez.proyectointegrado.repository.UsuarioRepository;
import com.velazquez.proyectointegrado.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    UsuarioRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<Usuario> findUsuarioById(Long id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<Usuario> findUsuarioByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            return null;
        }
        return userRepo.save(usuario);
    }

    @Override
    public void deleteUsuario(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public Optional<Usuario> login(String username, String password) {
        // Buscar al usuario por nombre de usuario
        Optional<Usuario> usuario = userRepo.findByUsername(username);

        if (usuario.isPresent() && passwordEncoder.matches(password, usuario.get().getPassword())) {
            // Si el usuario existe y la contraseña coincide, devuelve el usuario
            return usuario;
        } else {
            // Si el usuario no existe o la contraseña no coincide, devuelve null
            return null;
        }
    }

    @Override
    public Usuario insertUsuario(Usuario usuario) {

        if (usuario != null) {
            return userRepo.save(usuario);
        }

        return null;
    }

    @Override
    public List<Usuario> getUsuarios() {
        return userRepo.findAll();
    }
}
