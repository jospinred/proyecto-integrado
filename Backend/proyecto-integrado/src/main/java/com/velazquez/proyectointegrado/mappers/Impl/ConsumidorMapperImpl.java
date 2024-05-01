package com.velazquez.proyectointegrado.mappers.Impl;

import com.velazquez.proyectointegrado.api.dto.UsuarioDTO;
import com.velazquez.proyectointegrado.mappers.Mapper;
import com.velazquez.proyectointegrado.model.Consumidor;
import com.velazquez.proyectointegrado.model.Ofertante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConsumidorMapperImpl implements Mapper<Consumidor, UsuarioDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDTO mapTo(Consumidor consumidor) {
        return modelMapper.map(consumidor, UsuarioDTO.class);
    }

    @Override
    public Consumidor mapFrom(UsuarioDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Consumidor.class);
    }
}