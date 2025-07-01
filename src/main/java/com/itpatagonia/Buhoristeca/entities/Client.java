package com.itpatagonia.Buhoristeca.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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

    @Column(name = "idRole", nullable = false)
    private Integer idRole;
}
