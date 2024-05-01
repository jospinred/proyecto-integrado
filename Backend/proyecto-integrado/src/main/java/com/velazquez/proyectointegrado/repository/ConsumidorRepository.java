package com.velazquez.proyectointegrado.repository;

import com.velazquez.proyectointegrado.model.Consumidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumidorRepository extends JpaRepository<Consumidor, Long> {
    Optional<Consumidor> findByEmail(String email);
    Optional<Consumidor> findByUsername(String username);
}
