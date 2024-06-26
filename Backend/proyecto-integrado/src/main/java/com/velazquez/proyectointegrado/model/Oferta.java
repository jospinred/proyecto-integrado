package com.velazquez.proyectointegrado.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    @JoinColumn(name = "idCreador")
    private Ofertante creadorOferta;

    @ManyToOne
    @JoinColumn(name = "idActividad")
    private Actividad actividad;

    @Column(nullable = false)
    private String fecha;

    @Column(nullable = false)
    private String localidad;

    @Column(nullable = false)
    private String preparacionNecesaria;

    @Column(nullable = false)
    private Double tarifa;

    @Column(nullable = false)
    private String duracion;

    @Column(nullable = false)
    private int minimoPlazas;

    @Column(nullable = false)
    private int maximoPlazas;

    @Column
    private int participantes;

    @Column(nullable = false)
    private String descripcion;

    @Column
    private String materialOfertado;

    @Column
    private String materialNecesario;

    @ManyToMany(cascade = {CascadeType.ALL}, mappedBy = "actividadesApuntado")
    private Set<Consumidor> consumidoresApuntados = new HashSet<Consumidor>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ofertante getCreadorOferta() {
        return creadorOferta;
    }

    public void setCreadorOferta(Ofertante creadorOferta) {
        this.creadorOferta = creadorOferta;
    }

    public int getParticipantes() {
        return participantes;
    }

    public void setParticipantes(int participantes) {
        this.participantes = participantes;
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

    public String getPreparacionNecesaria() {
        return preparacionNecesaria;
    }

    public void setPreparacionNecesaria(String preparacionNecesaria) {
        this.preparacionNecesaria = preparacionNecesaria;
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

    public Set<Consumidor> getConsumidoresApuntados() {
        return consumidoresApuntados;
    }

    public void setConsumidoresApuntados(Set<Consumidor> consumidoresApuntados) {
        this.consumidoresApuntados = consumidoresApuntados;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "id=" + id +
                ", creadorOferta=" + creadorOferta +
                ", actividad=" + actividad +
                ", fecha='" + fecha + '\'' +
                ", localidad='" + localidad + '\'' +
                ", preparacionNecesaria='" + preparacionNecesaria + '\'' +
                ", tarifa=" + tarifa +
                ", duracion='" + duracion + '\'' +
                ", minimoPlazas=" + minimoPlazas +
                ", maximoPlazas=" + maximoPlazas +
                ", descripcion='" + descripcion + '\'' +
                ", materialOfertado='" + materialOfertado + '\'' +
                ", materialNecesario='" + materialNecesario + '\'' +
                '}';
    }
}
