package com.velazquez.proyectointegrado.services;

import com.velazquez.proyectointegrado.model.Actividad;


import java.util.List;
import java.util.Optional;

public interface ActividadService {
    Actividad insertActividad(Actividad actividad);
    List<Actividad> getActividades();
    Optional<Actividad> findActividadById(Long id);
    Optional<Actividad> findActividadByNombre(String nombre);
    Actividad updateActividad(Actividad actividad);
    void deleteActividad(Long id);
}
