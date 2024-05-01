package com.velazquez.proyectointegrado.services;

import com.velazquez.proyectointegrado.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario insertUsuario(Usuario usuario);
    List<Usuario> getUsuarios();
    Optional<Usuario> findUsuarioById(Long id);
    Usuario updateUsuario(Usuario usuario);
    void deleteUsuario(Long id);
    Optional<Usuario> login(String username, String password);
}
