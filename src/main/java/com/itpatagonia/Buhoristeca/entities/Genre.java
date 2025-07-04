package com.itpatagonia.Buhoristeca.entities;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idGenre", nullable = false)
    private Integer idGenre;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "genres")
    private Set<Book> books;

    @Override
    @JsonValue
    public String toString() {
        return name;
    }
}
