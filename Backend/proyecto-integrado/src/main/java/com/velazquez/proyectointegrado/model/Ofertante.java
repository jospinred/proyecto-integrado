package com.velazquez.proyectointegrado.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ofertantes")
@PrimaryKeyJoinColumn(name = "ofertanteId")
public class Ofertante extends Usuario{
    public Ofertante(){
        super();
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "OfertanteActividad",
            joinColumns = {@JoinColumn(name = "idOfertante")},
            inverseJoinColumns = {@JoinColumn(name = "idActividad")})
    private Set<Actividad> actividadesCualificado = new HashSet<Actividad>();

    @OneToMany(mappedBy = "creadorOferta", cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Oferta> ofertas;

    public Set<Actividad> getActividadesCualificado() {
        return actividadesCualificado;
    }

    public void setActividadCualificado(Actividad actividad) {
        this.actividadesCualificado.add(actividad);
    }

    public void delActividadCualificado(Actividad actividad) {
        this.actividadesCualificado.remove(actividad);
    }

    public void setActividadesCualificado(Set<Actividad> actividadesCualificado) {
        this.actividadesCualificado = actividadesCualificado;
    }
}
