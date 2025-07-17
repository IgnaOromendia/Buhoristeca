package com.itpatagonia.Buhoristeca.entities;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRole;

    @Column(name = "title")
    private String title;

    public Role(int idRole, String title) {
        this.idRole = idRole;
        this.title = title;
    }

    @Override
    @JsonValue
    public String toString() {
        return title;
    }
}
