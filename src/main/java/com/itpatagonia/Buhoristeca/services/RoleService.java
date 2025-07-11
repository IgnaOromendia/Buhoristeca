package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.entities.Role;
import com.itpatagonia.Buhoristeca.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role getRoleById(Integer idRole) {
        return assertRoleExists(idRole);
    }

    public Role assertRoleExists(Integer idRole) {
        Optional<Role> role = roleRepository.findById(idRole);

        if (role.isEmpty()) throw new RuntimeException("El rol con id " + idRole + " no existe");

        return role.get();
    }
}
