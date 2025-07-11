package com.itpatagonia.Buhoristeca.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itpatagonia.Buhoristeca.entities.BookState;
import com.itpatagonia.Buhoristeca.entities.Genre;

import java.time.LocalDate;
import java.util.Set;

public class BookCopyDto {

    @JsonProperty
    private final String title;

    @JsonProperty
    private final String description;

    @JsonProperty
    private final LocalDate publicationDate;

    @JsonProperty
    private final Set<Genre> genres;

    @JsonProperty
    private final BookState state;

    public BookCopyDto(String title, String description, LocalDate publicationDate, Set<Genre> genres, BookState state) {
        this.title = title;
        this.description = description;
        this.publicationDate = publicationDate;
        this.genres = genres;
        this.state = state;
    }

}
