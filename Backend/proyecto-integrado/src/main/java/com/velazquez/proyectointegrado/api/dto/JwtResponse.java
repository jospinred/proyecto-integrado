package com.velazquez.proyectointegrado.api.dto;

public class JwtResponse {
    private String token;
    private int permisos;
    private String username;

    public JwtResponse(String token, int permisos, String username) {
        this.token = token;
        this.permisos = permisos;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getPermisos() {
        return permisos;
    }

    public void setPermisos(int permisos) {
        this.permisos = permisos;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
