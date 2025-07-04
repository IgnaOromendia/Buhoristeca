package com.itpatagonia.Buhoristeca.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "publisher")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPublisher", nullable = false)
    private Integer idPublisher;

    @Column(name = "name", nullable = false)
    private String name;
}
