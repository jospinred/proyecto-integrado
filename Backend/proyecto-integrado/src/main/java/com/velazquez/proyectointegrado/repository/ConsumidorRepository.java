package com.velazquez.proyectointegrado.repository;

import com.velazquez.proyectointegrado.model.Consumidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConsumidorRepository extends JpaRepository<Consumidor, Long> {
    Optional<Consumidor> findByEmail(String email);
    Optional<Consumidor> findByUsername(String username);

    @Modifying
    @Query(value = "INSERT INTO consumidores (consumidor_id) VALUES (?1)", nativeQuery = true)
    void insertById(Long id);
}
