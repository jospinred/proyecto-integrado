package com.velazquez.proyectointegrado.repository;

import com.velazquez.proyectointegrado.model.Demanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandaRepository extends JpaRepository<Demanda, Long> {
    Demanda findDemandaById(Long id);

    @Query(value = "SELECT * FROM demandas WHERE id_creador = (?1)", nativeQuery = true)
    List<Demanda> findDemandasUsuario(Long id);
}
