package com.velazquez.proyectointegrado.services.Impl;

import com.velazquez.proyectointegrado.model.Oferta;
import com.velazquez.proyectointegrado.repository.OfertaRepository;
import com.velazquez.proyectointegrado.services.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfertaServiceImpl implements OfertaService {

    @Autowired
    OfertaRepository ofertaRepo;

    @Override
    public Oferta insertOferta(Oferta oferta) {
        if (oferta != null) {
            return ofertaRepo.save(oferta);
        }
        return null;
    }

    @Override
    public List<Oferta> getOfertas() {
        return ofertaRepo.findAll();
    }

    @Override
    public List<Oferta> getOfertasUsuario(Long id) {
        return ofertaRepo.findOfertasUsuario(id);
    }

    @Override
    public Optional<Oferta> findOfertaById(Long id) {
        return ofertaRepo.findById(id);
    }

    @Override
    public Oferta updateOferta(Oferta oferta) {
        if (oferta == null || oferta.getId() == null) {
            return null;
        }
        return ofertaRepo.save(oferta);
    }

    @Override
    public void deleteOferta(Long id) {
        ofertaRepo.deleteById(id);
    }
}
