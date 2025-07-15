package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.entities.Role;
import com.itpatagonia.Buhoristeca.exceptions.RoleNotFoundException;
import com.itpatagonia.Buhoristeca.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role getRoleById(Integer idRole) {
        return roleRepository.findById(idRole).orElseThrow(() -> new RoleNotFoundException(idRole));
    }

    public void assertRoleExists(Integer idRole) {
        if(!roleRepository.existsById(idRole)) throw new RoleNotFoundException(idRole);
    }
}
