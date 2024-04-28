package com.velazquez.proyectointegrado.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "administradores")
@PrimaryKeyJoinColumn(name = "adminId")
public class Admin extends Usuario{
    public Admin() {
        super();
    }
}
