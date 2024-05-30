package com.velazquez.proyectointegrado.services.Impl;

import com.velazquez.proyectointegrado.model.Demanda;
import com.velazquez.proyectointegrado.repository.DemandaRepository;
import com.velazquez.proyectointegrado.services.DemandaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemandaServiceImpl implements DemandaService {

    @Autowired
    DemandaRepository demandaRepo;

    @Override
    public Demanda insertDemanda(Demanda demanda) {
        if (demanda != null) {
            return demandaRepo.save(demanda);
        }
        return null;
    }

    @Override
    public List<Demanda> getDemandas() {
        return demandaRepo.findAll();
    }

    @Override
    public List<Demanda> getDemandasUsuario(Long id) {
        return demandaRepo.findDemandasUsuario(id);
    }

    @Override
    public Optional<Demanda> findDemandaById(Long id) {
        return demandaRepo.findById(id);
    }

    @Override
    public Demanda updateDemanda(Demanda demanda) {
        if (demanda == null || demanda.getId() == null) {
            return null;
        }
        return demandaRepo.save(demanda);
    }

    @Override
    public void deleteDemanda(Long id) {
        demandaRepo.deleteById(id);
    }
}
