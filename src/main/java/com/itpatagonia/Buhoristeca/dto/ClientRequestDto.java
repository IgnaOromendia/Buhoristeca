package com.itpatagonia.Buhoristeca.dto;

import com.itpatagonia.Buhoristeca.entities.Client;
import com.itpatagonia.Buhoristeca.entities.Role;

import java.time.LocalDate;

public class ClientRequestDto {
    
    private final Integer dni;
    private final String name;
    private final String lastName;
    private final LocalDate birthDate;
    private final String email;
    private final String address;
    private final Integer idRole;

    public ClientRequestDto(Integer dni, String name, String lastName, LocalDate birthDate, String email, String address, Integer idRole) {
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.address = address;
        this.idRole = idRole;
    }

    public Integer getClientId() {
        return dni;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public Client convertToClient(Role role) {
        return new Client(this.dni, this.name, this.lastName, this.birthDate, this.email, this.address, role);
    }

}
