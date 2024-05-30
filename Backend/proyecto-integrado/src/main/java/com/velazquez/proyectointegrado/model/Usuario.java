package com.velazquez.proyectointegrado.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
public class Usuario implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellidos;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fechaNacimiento;

    @Column
    private String sexo;

    @Column
    private String telefono;

    @Column(unique = true, nullable = false)
    private String email;
    @Column
    private boolean activo;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @OneToMany(mappedBy = "emisor", cascade = CascadeType.ALL)
    private Set<Notificacion> notificaciones_enviadas;

    @OneToMany(mappedBy = "receptor", cascade = CascadeType.ALL)
    private Set<Notificacion> notificaciones_recibidas;

    public Usuario() {}

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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Notificacion> getNotificaciones_enviadas() {
        return notificaciones_enviadas;
    }

    public void setNotificaciones_enviadas(Set<Notificacion> notificaciones_enviadas) {
        this.notificaciones_enviadas = notificaciones_enviadas;
    }

    public void addNotificacion_enviada(Notificacion notificacion) {
        this.notificaciones_enviadas.add(notificacion);
    }


    public void addNotificacion_recibida(Notificacion notificacion) {
        this.notificaciones_recibidas.add(notificacion);
    }



    public Set<Notificacion> getNotificaciones_recibidas() {
        return notificaciones_recibidas;
    }

    public void setNotificaciones_recibidas(Set<Notificacion> notificaciones_recibidas) {
        this.notificaciones_recibidas = notificaciones_recibidas;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fechaNacimiento='" + fechaNacimiento + '\'' +
                ", sexo='" + sexo + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", activo=" + activo +
                ", notificaciones_enviadas=" + notificaciones_enviadas +
                ", notificaciones_recibidas=" + notificaciones_recibidas +
                '}';
    }
}
