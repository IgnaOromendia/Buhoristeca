package com.itpatagonia.Buhoristeca.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itpatagonia.Buhoristeca.projections.BookCopiesAmountProjection;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BookCopiesAmountDto other = (BookCopiesAmountDto) obj;
        return Objects.equals(title, other.title) &&
                Objects.equals(amountOfCopies, other.amountOfCopies) &&
                Objects.equals(amountOfAvailableCopies, other.amountOfAvailableCopies);
    }
}
