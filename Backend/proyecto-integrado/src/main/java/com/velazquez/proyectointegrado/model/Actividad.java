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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Ofertante> getOfertantes() {
        return ofertantes;
    }

    public void setOfertantes(Set<Ofertante> ofertantes) {
        this.ofertantes = ofertantes;
    }

    public Set<Consumidor> getConsumidores() {
        return consumidores;
    }

    public void setConsumidores(Set<Consumidor> consumidores) {
        this.consumidores = consumidores;
    }

    public Set<Demanda> getDemandas() {
        return demandas;
    }

    public void setDemandas(Set<Demanda> demandas) {
        this.demandas = demandas;
    }

    public Set<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(Set<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
