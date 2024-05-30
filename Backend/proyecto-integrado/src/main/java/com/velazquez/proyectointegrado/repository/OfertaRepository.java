package com.velazquez.proyectointegrado.repository;

import com.velazquez.proyectointegrado.model.Oferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfertaRepository extends JpaRepository<Oferta, Long> {
    Oferta findOfertaById(Long id);


    @Query(value = "SELECT * FROM ofertas WHERE id_creador = (?1)", nativeQuery = true)
    List<Oferta> findOfertasUsuario(Long id);
}
