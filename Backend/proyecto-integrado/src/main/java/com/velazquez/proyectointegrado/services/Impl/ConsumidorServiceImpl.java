package com.velazquez.proyectointegrado.services.Impl;

import com.velazquez.proyectointegrado.model.Actividad;
import com.velazquez.proyectointegrado.model.Consumidor;
import com.velazquez.proyectointegrado.model.Oferta;
import com.velazquez.proyectointegrado.repository.ConsumidorRepository;
import com.velazquez.proyectointegrado.services.ConsumidorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    @Transactional
    @Override
    public Long insertConsumidorId(Long id) {
        if(id != null){
            consumidorRepo.insertById(id);
            return 1L;
        }
        return 0L;
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
    public Optional<Consumidor> findConsumidorByUsername(String username) {
        return consumidorRepo.findByUsername(username);
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
    @Override
    public int selectById(Long id) {
        return consumidorRepo.selectById(id);
    }

    @Override
    @Transactional
    public Consumidor apuntarOferta(Consumidor consumidor, Oferta oferta) {
        consumidor.setActividadesApuntado(oferta);
        return consumidorRepo.save(consumidor);
    }

    @Override
    public Boolean comprobarApuntadoOferta(Consumidor usuario, Oferta oferta) {
        Set<Oferta> actividadesApuntado;
        actividadesApuntado = usuario.getActividadesApuntado();
        return actividadesApuntado.contains(oferta);
    }
}
