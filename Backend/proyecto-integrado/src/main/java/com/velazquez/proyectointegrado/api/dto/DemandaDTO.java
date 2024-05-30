package com.velazquez.proyectointegrado.api.dto;

import com.velazquez.proyectointegrado.model.Actividad;
import com.velazquez.proyectointegrado.model.Consumidor;

import java.io.Serializable;

public class DemandaDTO implements Serializable {
    private Long id;
    private String creadorDemanda;
    private String actividad;
    private String fecha;
    private String localidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreadorDemanda() {
        return creadorDemanda;
    }

    public void setCreadorDemanda(String creadorDemanda) {
        this.creadorDemanda = creadorDemanda;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    @Override
    public String toString() {
        return "DemandaDTO{" +
                "id=" + id +
                ", creadorDemanda='" + creadorDemanda + '\'' +
                ", actividad='" + actividad + '\'' +
                ", fecha='" + fecha + '\'' +
                ", localidad='" + localidad + '\'' +
                '}';
    }
}
