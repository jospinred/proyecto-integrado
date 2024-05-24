package com.velazquez.proyectointegrado.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface JPAUserDetailsService {
    UserDetails loadUserByUsername(String email);
}
