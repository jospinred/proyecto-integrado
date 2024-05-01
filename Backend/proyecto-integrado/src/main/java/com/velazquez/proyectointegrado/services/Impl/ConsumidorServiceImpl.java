package com.velazquez.proyectointegrado.services.Impl;

import com.velazquez.proyectointegrado.model.Consumidor;
import com.velazquez.proyectointegrado.repository.ConsumidorRepository;
import com.velazquez.proyectointegrado.services.ConsumidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumidorServiceImpl implements ConsumidorService {

    @Autowired
    ConsumidorRepository consumidorRepo;
    @Override
    public Consumidor insertConsumidor(Consumidor consumidor) {
        if (consumidor != null) {
            return consumidorRepo.save(consumidor);
        }
        return null;
    }

    @Override
    public List<Consumidor> getConsumidor() {
        return consumidorRepo.findAll();
    }

    @Override
    public Optional<Consumidor> findUsuarioById(Long id) {
        return consumidorRepo.findById(id);
    }

    @Override
    public Consumidor updateConsumidor(Consumidor consumidor) {
        if (consumidor == null || consumidor.getId() == null) {
            return null;
        }
        return consumidorRepo.save(consumidor);
    }

    @Override
    public void deleteConsumidor(Long id) {
        consumidorRepo.deleteById(id);
    }
}
