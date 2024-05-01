package com.velazquez.proyectointegrado.repository;

import com.velazquez.proyectointegrado.model.Ofertante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfertanteRepository extends JpaRepository<Ofertante, Long> {
    Optional<Ofertante> findByEmail(String email);
    Optional<Ofertante> findByUsername(String username);
}
