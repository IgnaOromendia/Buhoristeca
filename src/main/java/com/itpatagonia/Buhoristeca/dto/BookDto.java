package com.itpatagonia.Buhoristeca.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class BookDto {

    @JsonProperty
    private final String title;

    @JsonProperty
    private final String description;

    @JsonProperty
    private final LocalDate publicationDate;

    public BookDto(String title, String description, LocalDate publicationDate) {
        this.title = title;
        this.description = description;
        this.publicationDate = publicationDate;
    }

}
