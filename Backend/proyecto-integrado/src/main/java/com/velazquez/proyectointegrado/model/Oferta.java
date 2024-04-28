package com.velazquez.proyectointegrado.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ofertas")
public class Oferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idCreador")
    private Ofertante creadorOferta;

    @ManyToOne
    @JoinColumn(name = "idActividad")
    private Actividad actividad;

    @Column(nullable = false)
    private String fecha;

    @Column(nullable = false)
    private String direccion;

    //TODO: faltan propiedades de las ofertas, terminadas las relaciones



    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "actividadesApuntado")
    private Set<Consumidor> consumidoresApuntados = new HashSet<Consumidor>();

}
