package com.velazquez.proyectointegrado.repository;

import com.velazquez.proyectointegrado.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    Actividad findActividadById(Long id);
    Actividad findActividadByNombre(String nombre);
}
