package com.velazquez.proyectointegrado.services;



import com.velazquez.proyectointegrado.model.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    Admin insertAdmin(Admin usuario);
    List<Admin> getAdmins();
    Optional<Admin> findUsuarioById(Long id);
    Admin updateAdmin(Admin usuario);
    void deleteAdmin(Long id);
}
