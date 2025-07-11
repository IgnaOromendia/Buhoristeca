package com.itpatagonia.Buhoristeca.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class LoanDto {

    @JsonProperty
    private final String bookTitle;

    @JsonProperty
    private final Integer copyNumber;

    @JsonProperty
    private final LocalDate loanDate;

    @JsonProperty
    private final LocalDate returnDate;

    @JsonProperty
    private final LocalDate limitReturnDate;

    @JsonProperty
    private final String name;

    @JsonProperty
    private final String lastName;

    public LoanDto(String bookTitle, Integer copyNumber, String name, String lastName, LocalDate loanDate, LocalDate returnDate, LocalDate limitReturnDate) {
        this.bookTitle = bookTitle;
        this.copyNumber = copyNumber;
        this.name = name;
        this.lastName = lastName;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.limitReturnDate = limitReturnDate;
    }

}
