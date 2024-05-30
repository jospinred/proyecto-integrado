package com.velazquez.proyectointegrado.services;

import com.velazquez.proyectointegrado.model.Oferta;

import java.util.List;
import java.util.Optional;

public interface OfertaService {
    Oferta insertOferta(Oferta oferta);
    List<Oferta> getOfertas();
    List<Oferta> getOfertasUsuario(Long id);
    Optional<Oferta> findOfertaById(Long id);
    Oferta updateOferta(Oferta oferta);
    void deleteOferta(Long id);
}
