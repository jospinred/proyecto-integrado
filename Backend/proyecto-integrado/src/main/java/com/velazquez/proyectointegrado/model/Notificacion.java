package com.velazquez.proyectointegrado.model;

import jakarta.persistence.*;

@Entity
@Table(name = "notificaciones")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "IdEmisor")
    private Usuario emisor;

    @ManyToOne
    @JoinColumn(name = "IdReceptor")
    private Usuario receptor;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Boolean leido;
}
