package com.velazquez.proyectointegrado.api.dto;

public class PreferenciaDTO {
    private Long id;
    private String nombre;

    private String operacion;

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

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    @Override
    public String toString() {
        return "PreferenciaDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", operacion='" + operacion + '\'' +
                '}';
    }
}
