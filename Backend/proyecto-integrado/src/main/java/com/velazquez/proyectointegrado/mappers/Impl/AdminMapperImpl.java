package com.velazquez.proyectointegrado.mappers.Impl;

import com.velazquez.proyectointegrado.api.dto.UsuarioDTO;
import com.velazquez.proyectointegrado.mappers.Mapper;
import com.velazquez.proyectointegrado.model.Admin;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminMapperImpl implements Mapper<Admin, UsuarioDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UsuarioDTO mapTo(Admin admin) {
        return modelMapper.map(admin, UsuarioDTO.class);
    }

    @Override
    public Admin mapFrom(UsuarioDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Admin.class);
    }
}