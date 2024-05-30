package com.velazquez.proyectointegrado.mappers.Impl;

import com.velazquez.proyectointegrado.api.dto.DemandaDTO;
import com.velazquez.proyectointegrado.mappers.Mapper;
import com.velazquez.proyectointegrado.model.Demanda;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemandaMapperImpl implements Mapper<Demanda, DemandaDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DemandaDTO mapTo(Demanda demanda) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper.map(demanda, DemandaDTO.class);
    }

    @Override
    public Demanda mapFrom(DemandaDTO demandaDTO) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper.map(demandaDTO, Demanda.class);
    }
}