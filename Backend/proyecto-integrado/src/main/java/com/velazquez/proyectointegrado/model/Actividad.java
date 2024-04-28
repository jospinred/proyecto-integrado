package com.velazquez.proyectointegrado.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "actividades")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "actividadesCualificado")
    private Set<Ofertante> ofertantes = new HashSet<Ofertante>();

    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "actividadesPreferencia")
    private Set<Consumidor> consumidores = new HashSet<Consumidor>();

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL)
    private Set<Demanda> demandas;

    @OneToMany(mappedBy = "actividad", cascade = CascadeType.ALL)
    private Set<Oferta> ofertas;
}
