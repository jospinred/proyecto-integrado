package com.velazquez.proyectointegrado.services;

import com.velazquez.proyectointegrado.model.Ofertante;

import java.util.List;
import java.util.Optional;

public interface OfertanteService {
    Ofertante insertOfertante(Ofertante usuario);
    Long insertOfertanteId(Long id);
    List<Ofertante> getOfertantes();
    Optional<Ofertante> findUsuarioById(Long id);
    Ofertante updateOfertante(Ofertante usuario);
    void deleteOfertante(Long id);
}
