package com.velazquez.proyectointegrado.repository;

import com.velazquez.proyectointegrado.model.Ofertante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfertanteRepository extends JpaRepository<Ofertante, Long> {
    Optional<Ofertante> findByEmail(String email);
    Optional<Ofertante> findByUsername(String username);

    @Modifying
    @Query(value = "INSERT INTO ofertantes (ofertante_id) VALUES (?1)", nativeQuery = true)
    void insertById(Long id);

    @Query(value = "SELECT COUNT(*) FROM ofertantes WHERE ofertante_id = (?1)", nativeQuery = true)
    int selectById(Long id);
}
