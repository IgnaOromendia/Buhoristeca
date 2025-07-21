package com.itpatagonia.Buhoristeca.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itpatagonia.Buhoristeca.entities.LoanState;

import java.time.LocalDate;
import java.util.Objects;

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
    private final LoanState stauts;

    @JsonProperty
    private final String clientName;

    @JsonProperty
    private final String clientLastName;

    public LoanDto(String bookTitle, Integer copyNumber, String clientName, String clientLastName, LoanState status, LocalDate loanDate, LocalDate returnDate, LocalDate limitReturnDate) {
        this.bookTitle = bookTitle;
        this.copyNumber = copyNumber;
        this.clientName = clientName;
        this.clientLastName = clientLastName;
        this.stauts = status;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.limitReturnDate = limitReturnDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LoanDto other = (LoanDto) obj;
        return Objects.equals(bookTitle, other.bookTitle) &&
                Objects.equals(copyNumber, other.copyNumber) &&
                Objects.equals(loanDate, other.loanDate) &&
                Objects.equals(returnDate, other.returnDate) &&
                Objects.equals(limitReturnDate, other.limitReturnDate) &&
                Objects.equals(stauts, other.stauts) &&
                Objects.equals(clientName, other.clientName) &&
                Objects.equals(clientLastName, other.clientLastName);
    }
}
