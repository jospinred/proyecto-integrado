package com.velazquez.proyectointegrado.services;

import com.velazquez.proyectointegrado.model.Ofertante;

import java.util.List;
import java.util.Optional;

public interface OfertanteService {
    Ofertante insertOfertante(Ofertante usuario);
    Long insertOfertanteId(Long id);
    List<Ofertante> getOfertantes();
    Optional<Ofertante> findOfertanteById(Long id);
    Optional<Ofertante> findOfertanteByUsername(String username);
    Ofertante updateOfertante(Ofertante usuario);
    void deleteOfertante(Long id);
    int selectById(Long id);
}
