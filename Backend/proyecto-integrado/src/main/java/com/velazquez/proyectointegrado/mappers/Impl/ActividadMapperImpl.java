package com.velazquez.proyectointegrado.mappers.Impl;


import com.velazquez.proyectointegrado.api.dto.ActividadDTO;
import com.velazquez.proyectointegrado.mappers.Mapper;
import com.velazquez.proyectointegrado.model.Actividad;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ActividadMapperImpl implements Mapper<Actividad, ActividadDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ActividadDTO mapTo(Actividad actividad) {
        return modelMapper.map(actividad, ActividadDTO.class);
    }

    @Override
    public Actividad mapFrom(ActividadDTO actividadDTO) {
        return modelMapper.map(actividadDTO, Actividad.class);
    }
}