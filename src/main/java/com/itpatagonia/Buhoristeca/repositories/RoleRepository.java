package com.itpatagonia.Buhoristeca.repositories;

import com.itpatagonia.Buhoristeca.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
