package com.itpatagonia.Buhoristeca.entities;

import com.itpatagonia.Buhoristeca.dto.LoanDto;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLoan", nullable = false)
    private Integer idLoan;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "idBook", nullable = false),
            @JoinColumn(name = "idBookCopy", nullable = false)
    })
    private BookCopy bookCopy;

    @ManyToOne
    @JoinColumn(name = "dni", nullable = false)
    private Client client;

    @Column(name = "loanDate", nullable = false)
    private LocalDate loanDate;

    @Column(name = "returnDate")
    private LocalDate returnDate;

    @Column(name = "limitReturnDate", nullable = false)
    private LocalDate limitReturnDate;

    public Loan() {}

    public Loan(BookCopy bookCopy, Client client) {
        this.bookCopy = bookCopy;
        this.client = client;
        this.loanDate = LocalDate.now();
        this.limitReturnDate = LocalDate.now().plusDays(14);
    }

    public LoanDto convertToDto() {
        StringBuilder bookTitle = new StringBuilder();
        StringBuilder copyNumber = new StringBuilder();
        StringBuilder clientName = new StringBuilder();
        StringBuilder clientLastName = new StringBuilder();

        this.client.addNameInformationTo(clientName, clientLastName);
        this.bookCopy.addBookInformationTo(bookTitle, copyNumber);

        return new LoanDto(bookTitle.toString(), Integer.parseInt(copyNumber.toString()), clientName.toString(), clientLastName.toString());
    }
}
