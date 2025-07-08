package com.itpatagonia.Buhoristeca.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoanDto {

    @JsonProperty
    private final String bookTitle;

    @JsonProperty
    private final Integer copyNumber;

    @JsonProperty
    private final String name;

    @JsonProperty
    private final String lastName;

    public LoanDto(String bookTitle, Integer copyNumber, String name, String lastName) {
        this.bookTitle = bookTitle;
        this.copyNumber = copyNumber;
        this.name = name;
        this.lastName = lastName;
    }

}
