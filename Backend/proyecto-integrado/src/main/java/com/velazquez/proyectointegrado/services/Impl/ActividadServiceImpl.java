package com.velazquez.proyectointegrado.services.Impl;

import com.velazquez.proyectointegrado.model.Actividad;
import com.velazquez.proyectointegrado.model.Oferta;
import com.velazquez.proyectointegrado.repository.ActividadRepository;
import com.velazquez.proyectointegrado.repository.OfertaRepository;
import com.velazquez.proyectointegrado.services.ActividadService;
import com.velazquez.proyectointegrado.services.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActividadServiceImpl implements ActividadService {

    @Autowired
    ActividadRepository actividadRepo;

    @Override
    public Actividad insertActividad(Actividad actividad) {
        if (actividad != null) {
            return actividadRepo.save(actividad);
        }
        return null;
    }

    @Override
    public List<Actividad> getActividades() {
        return actividadRepo.findAll();
    }

    @Override
    public Optional<Actividad> findActividadById(Long id) {
        return actividadRepo.findById(id);
    }

    @Override
    public Optional<Actividad> findActividadByNombre(String nombre) {
        return Optional.ofNullable(actividadRepo.findActividadByNombre(nombre));
    }

    @Override
    public Actividad updateActividad(Actividad actividad) {
        if (actividad == null || actividad.getId() == null) {
            return null;
        }
        return actividadRepo.save(actividad);
    }

    @Override
    public void deleteActividad(Long id) {
        actividadRepo.deleteById(id);
    }
}
