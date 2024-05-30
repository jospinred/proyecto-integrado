package com.velazquez.proyectointegrado.api.dto;

import com.velazquez.proyectointegrado.model.Actividad;
import com.velazquez.proyectointegrado.model.Consumidor;
import com.velazquez.proyectointegrado.model.Ofertante;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
public class OfertaDTO {
    private Long id;
    private String creadorOferta;
    private String actividad;
    private String fecha;
    private String localidad;
    private String preparacionNecesaria;
    private Double tarifa;
    private String duracion;
    private int minimoPlazas;
    private int maximoPlazas;
    private int participantes;
    private String descripcion;
    private String materialOfertado;
    private String materialNecesario;
    public String getCreadorOferta() {
        return creadorOferta;
    }
    public void setCreadorOferta(String creadorOferta) {
        this.creadorOferta = creadorOferta;
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
    public int getParticipantes() {
        return participantes;
    }
    public void setParticipantes(int participantes) {
        this.participantes = participantes;
    }
    public String getPreparacionNecesaria() {
        return preparacionNecesaria;
    }
    public void setPreparacionNecesaria(String preparacionNecesaria) {
        this.preparacionNecesaria = preparacionNecesaria;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getTarifa() {
        return tarifa;
    }
    public void setTarifa(Double tarifa) {
        this.tarifa = tarifa;
    }
    public String getDuracion() {
        return duracion;
    }
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }
    public int getMinimoPlazas() {
        return minimoPlazas;
    }
    public void setMinimoPlazas(int minimoPlazas) {
        this.minimoPlazas = minimoPlazas;
    }
    public int getMaximoPlazas() {
        return maximoPlazas;
    }
    public void setMaximoPlazas(int maximoPlazas) {
        this.maximoPlazas = maximoPlazas;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getMaterialOfertado() {
        return materialOfertado;
    }
    public void setMaterialOfertado(String materialOfertado) {
        this.materialOfertado = materialOfertado;
    }
    public String getMaterialNecesario() {
        return materialNecesario;
    }
    public void setMaterialNecesario(String materialNecesario) {
        this.materialNecesario = materialNecesario;
    }

    @Override
    public String toString() {
        return "OfertaDTO{" +
                "creadorOferta='" + creadorOferta + '\'' +
                ", actividad='" + actividad + '\'' +
                ", fecha='" + fecha + '\'' +
                ", localidad='" + localidad + '\'' +
                ", preparacionNecesaria='" + preparacionNecesaria + '\'' +
                ", tarifa=" + tarifa +
                ", duracion='" + duracion + '\'' +
                ", minimoPlazas=" + minimoPlazas +
                ", maximoPlazas=" + maximoPlazas +
                ", participantes=" + participantes +
                ", descripcion='" + descripcion + '\'' +
                ", materialOfertado='" + materialOfertado + '\'' +
                ", materialNecesario='" + materialNecesario + '\'' +
                '}';
    }
}
