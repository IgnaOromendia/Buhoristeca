package com.itpatagonia.Buhoristeca.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @Column(name = "idLoan", nullable = false)
    private Integer idLoan;

    @Column(name = "idBook", nullable = false)
    private Integer idBook;

    @Column(name = "idBookCopy", nullable = false)
    private Integer idBookCopy;

    @Column(name = "dni", nullable = false)
    private Integer dni;

    @Column(name = "loanDate", nullable = false)
    private LocalDate loanDate;

    @Column(name = "returnDate")
    private LocalDate returnDate;

    @Column(name = "limitReturnDate", nullable = false)
    private LocalDate limitReturnDate;
}
