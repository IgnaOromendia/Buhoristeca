package com.itpatagonia.Buhoristeca.entities;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;

@Entity
@Table(name = "bookState")
public class BookState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idState", nullable = false)
    private Integer idState;

    @Column(name = "title", nullable = false)
    private String title;

    @Override
    @JsonValue
    public String toString() {
        return title;
    }
}
