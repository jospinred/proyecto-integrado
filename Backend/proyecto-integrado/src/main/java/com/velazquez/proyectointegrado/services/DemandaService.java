package com.velazquez.proyectointegrado.services;

import com.velazquez.proyectointegrado.model.Demanda;

import java.util.List;
import java.util.Optional;

public interface DemandaService {
    Demanda insertDemanda(Demanda demanda);
    List<Demanda> getDemandas();
    List<Demanda> getDemandasUsuario(Long id);
    Optional<Demanda> findDemandaById(Long id);
    Demanda updateDemanda(Demanda demanda);
    void deleteDemanda(Long id);
}
