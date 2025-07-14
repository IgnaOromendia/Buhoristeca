package com.itpatagonia.Buhoristeca.entities;

import com.itpatagonia.Buhoristeca.dto.ClientDto;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "dni", nullable = false)
    private Integer dni;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "lastName", nullable = false, length = 30)
    private String lastName;

    @Column(name = "birthDate", nullable = false)
    private LocalDate birthDate;

    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @Column(name = "address", nullable = false, length = 30)
    private String address;

    @Column(name = "isActive", nullable = false)
    private Integer isActive;

    @ManyToOne
    @JoinColumn(name = "idRole", nullable = false)
    private Role role;

    public Client(Integer dni, String name, String lastName, LocalDate birthDate, String email, String address, Role role) {
        this.dni = dni;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.address = address;
        this.role = role;
        this.isActive = 1;
    }

    public Client() {}

    public ClientDto converToClientDto() {
        return new ClientDto(this.name, this.lastName, this.role, this.isActive);
    }

    public void addNameInformationTo(StringBuilder clientName, StringBuilder clientLastName) {
        clientName.append(this.name);
        clientLastName.append(this.lastName);
    }
}
