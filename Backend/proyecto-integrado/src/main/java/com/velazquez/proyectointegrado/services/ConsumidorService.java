package com.velazquez.proyectointegrado.services;

import com.velazquez.proyectointegrado.model.Consumidor;

import java.util.List;
import java.util.Optional;

public interface ConsumidorService {
    Consumidor insertConsumidor(Consumidor usuario);
    List<Consumidor> getConsumidor();
    Optional<Consumidor> findUsuarioById(Long id);
    Consumidor updateConsumidor(Consumidor usuario);
    void deleteConsumidor(Long id);
}
