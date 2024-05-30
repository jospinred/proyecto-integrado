package com.velazquez.proyectointegrado.mappers.Impl;


import com.velazquez.proyectointegrado.api.dto.OfertaDTO;
import com.velazquez.proyectointegrado.mappers.Mapper;
import com.velazquez.proyectointegrado.model.Oferta;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OfertaMapperImpl implements Mapper<Oferta, OfertaDTO> {
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public OfertaDTO mapTo(Oferta oferta) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper.map(oferta, OfertaDTO.class);
    }

    @Override
    public Oferta mapFrom(OfertaDTO ofertaDTO) {
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        return modelMapper.map(ofertaDTO, Oferta.class);
    }
}