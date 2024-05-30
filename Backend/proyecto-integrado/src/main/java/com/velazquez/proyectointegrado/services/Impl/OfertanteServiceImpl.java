package com.velazquez.proyectointegrado.services.Impl;

import com.velazquez.proyectointegrado.model.Ofertante;

import com.velazquez.proyectointegrado.repository.OfertanteRepository;
import com.velazquez.proyectointegrado.services.OfertanteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfertanteServiceImpl implements OfertanteService {

    @Autowired
    OfertanteRepository ofertanteRepo;
    @Override
    public Ofertante insertOfertante(Ofertante ofertante) {
        if (ofertante != null) {
            return ofertanteRepo.save(ofertante);
        }
        return null;
    }

    @Transactional
    @Override
    public Long insertOfertanteId(Long id) {
        if(id != null){
            ofertanteRepo.insertById(id);
            return 1L;
        }
        return 0L;
    }

    @Override
    public List<Ofertante> getOfertantes() {
        return ofertanteRepo.findAll();
    }

    @Override
    public Optional<Ofertante> findOfertanteById(Long id) {
        return ofertanteRepo.findById(id);
    }

    @Override
    public Optional<Ofertante> findOfertanteByUsername(String username) {
        return ofertanteRepo.findByUsername(username);
    }

    @Override
    public Ofertante updateOfertante(Ofertante ofertante) {
        if (ofertante == null || ofertante.getId() == null) {
            return null;
        }
        return ofertanteRepo.save(ofertante);
    }

    @Override
    public void deleteOfertante(Long id) {
        ofertanteRepo.deleteById(id);
    }

    @Override
    public int selectById(Long id) {
        return ofertanteRepo.selectById(id);
    }
}
