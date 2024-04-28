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
    private String direccion;
}
