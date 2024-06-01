package com.velazquez.proyectointegrado.services;

import com.velazquez.proyectointegrado.model.Consumidor;
import com.velazquez.proyectointegrado.model.Oferta;

import java.util.List;
import java.util.Optional;

public interface ConsumidorService {
    Consumidor insertConsumidor(Consumidor usuario);
    Long insertConsumidorId(Long id);
    List<Consumidor> getConsumidor();
    Optional<Consumidor> findUsuarioById(Long id);
    Optional<Consumidor> findConsumidorByUsername(String username);
    Consumidor updateConsumidor(Consumidor usuario);
    void deleteConsumidor(Long id);
    int selectById(Long id);
    //Consumidor apuntarOferta(Consumidor usuario, Oferta oferta);
    Boolean comprobarApuntadoOferta(Consumidor usuario, Oferta oferta);
}
