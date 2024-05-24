package com.velazquez.proyectointegrado.services.Impl;

import com.velazquez.proyectointegrado.model.JPAUserDetails;
import com.velazquez.proyectointegrado.model.Usuario;
import com.velazquez.proyectointegrado.repository.UsuarioRepository;
import com.velazquez.proyectointegrado.services.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JPAUserDetailsServiceImpl implements JPAUserDetailsService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));
        return new JPAUserDetails(usuario);
    }
}
