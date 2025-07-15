package com.itpatagonia.Buhoristeca.entities;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;

@Entity
@Table(name = "loanState")
public class LoanState {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idState;

    @Column(name = "title", nullable = false)
    private String title;

    @Override
    @JsonValue
    public String toString() {
        return title;
    }
}
