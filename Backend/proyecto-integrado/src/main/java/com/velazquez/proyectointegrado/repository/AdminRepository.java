package com.velazquez.proyectointegrado.repository;

import com.velazquez.proyectointegrado.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByUsername(String username);

    @Modifying
    @Query(value = "INSERT INTO administradores (admin_id) VALUES (?1)", nativeQuery = true)
    void insertById(Long id);
}
