package com.velazquez.proyectointegrado.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "consumidores")
@PrimaryKeyJoinColumn(name = "ConsumidorId")
public class Consumidor extends Usuario{
    public Consumidor(){
        super();
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "ConsumidorActividad",
            joinColumns = {@JoinColumn(name = "idConsumidor")},
            inverseJoinColumns = {@JoinColumn(name = "idActividad")})
    private Set<Actividad> actividadesPreferencia = new HashSet<Actividad>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "ConsumidorOferta",
            joinColumns = {@JoinColumn(name = "idConsumidor")},
            inverseJoinColumns = {@JoinColumn(name = "idActividad")})
    private Set<Oferta> actividadesApuntado = new HashSet<Oferta>();

    @OneToMany(mappedBy = "creadorDemanda", cascade = CascadeType.ALL)
    private Set<Demanda> demandas;
}
