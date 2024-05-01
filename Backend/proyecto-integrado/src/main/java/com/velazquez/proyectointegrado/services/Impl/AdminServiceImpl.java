package com.velazquez.proyectointegrado.services.Impl;

import com.velazquez.proyectointegrado.model.Admin;
import com.velazquez.proyectointegrado.repository.AdminRepository;
import com.velazquez.proyectointegrado.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminRepository adminRepo;
    @Override
    public Admin insertAdmin(Admin admin) {
        if (admin != null) {
            return adminRepo.save(admin);
        }
        return null;
    }

    @Override
    public List<Admin> getAdmins() {
        return adminRepo.findAll();
    }

    @Override
    public Optional<Admin> findUsuarioById(Long id) {
        return adminRepo.findById(id);
    }

    @Override
    public Admin updateAdmin(Admin ofertante) {
        if (ofertante == null || ofertante.getId() == null) {
            return null;
        }
        return adminRepo.save(ofertante);
    }

    @Override
    public void deleteAdmin(Long id) {
        adminRepo.deleteById(id);
    }
}
