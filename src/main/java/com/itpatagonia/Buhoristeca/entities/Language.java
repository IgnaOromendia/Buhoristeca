package com.itpatagonia.Buhoristeca.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "bookLanguage")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLanguage", nullable = false)
    private Integer idLanguage;

    @Column(name = "title", nullable = false)
    private String title;
}
