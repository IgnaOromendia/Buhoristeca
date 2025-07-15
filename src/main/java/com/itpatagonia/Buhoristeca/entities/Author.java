package com.itpatagonia.Buhoristeca.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Author {

    @Id
    @Column(name = "idAuthor", nullable = false)
    private Integer idAuthor;

    @Column(name = "name", nullable = false)
    private String name;

    public void addNameTo(StringBuilder bookAuthor) {
        bookAuthor.append(this.name);
    }
}
