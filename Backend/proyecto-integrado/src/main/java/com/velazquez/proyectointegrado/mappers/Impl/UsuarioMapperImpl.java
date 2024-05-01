package com.velazquez.proyectointegrado.mappers.Impl;

import org.modelmapper.ModelMapper;
import com.velazquez.proyectointegrado.api.dto.UsuarioDTO;
import com.velazquez.proyectointegrado.mappers.Mapper;
import com.velazquez.proyectointegrado.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapperImpl implements Mapper<Usuario, UsuarioDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDTO mapTo(Usuario usuario) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @Override
    public Usuario mapFrom(UsuarioDTO usuarioDTO) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper.map(usuarioDTO, Usuario.class);
    }
}