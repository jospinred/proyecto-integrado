package com.velazquez.proyectointegrado.mappers.Impl;

import com.velazquez.proyectointegrado.api.dto.UsuarioDTO;
import com.velazquez.proyectointegrado.mappers.Mapper;
import com.velazquez.proyectointegrado.model.Ofertante;
import com.velazquez.proyectointegrado.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfertanteMapperImpl implements Mapper<Ofertante, UsuarioDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDTO mapTo(Ofertante ofertante) {
        return modelMapper.map(ofertante, UsuarioDTO.class);
    }

    @Override
    public Ofertante mapFrom(UsuarioDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Ofertante.class);
    }
}