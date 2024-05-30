package com.velazquez.proyectointegrado.api.dto;

public class UpdatePassDTO {
    private Long id;
    private String password;

    // Constructores, getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
