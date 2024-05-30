package com.velazquez.proyectointegrado.model;

import jakarta.persistence.*;

@Entity
@Table(name = "demandas")
public class Demanda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCreador")
    private Consumidor creadorDemanda;

    @ManyToOne
    @JoinColumn(name = "idActividad")
    private Actividad actividad;

    @Column(nullable = false)
    private String fecha;

    @Column(nullable = false)
    private String localidad;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Consumidor getCreadorDemanda() {
        return creadorDemanda;
    }

    public void setCreadorDemanda(Consumidor creadorDemanda) {
        this.creadorDemanda = creadorDemanda;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
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
}
