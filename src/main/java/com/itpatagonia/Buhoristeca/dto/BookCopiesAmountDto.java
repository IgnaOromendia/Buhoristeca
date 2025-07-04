package com.itpatagonia.Buhoristeca.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itpatagonia.Buhoristeca.projections.BookCopiesAmountProjection;

public class BookCopiesAmountDto {

    @JsonProperty
    private String title;

    @JsonProperty
    private Integer amountOfCopies;

    @JsonProperty
    private Integer amountOfAvailableCopies;

    public BookCopiesAmountDto(BookCopiesAmountProjection projection) {
        this.title = projection.getTitle();
        this.amountOfCopies = projection.getAmountOfCopies();
        this.amountOfAvailableCopies = projection.getAmountOfAvailableCopies();
    }

}
