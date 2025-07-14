package com.itpatagonia.Buhoristeca.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itpatagonia.Buhoristeca.entities.BookState;
import com.itpatagonia.Buhoristeca.entities.Genre;

import java.time.LocalDate;
import java.util.Set;

public class BookDto {

    @JsonProperty
    private final String title;

    @JsonProperty
    private final String description;

    @JsonProperty
    private final LocalDate publicationDate;

    @JsonProperty
    private final Integer status;

    @JsonProperty
    private final Set<Genre> genres;

    public BookDto(String title, String description, LocalDate publicationDate, Set<Genre> genres, Integer status) {
        this.title = title;
        this.description = description;
        this.publicationDate = publicationDate;
        this.genres = genres;
        this.status = status;
    }

    public BookCopyDto converToBookCopyDto(BookState state) {
        return new BookCopyDto(this.title, this.description, this.publicationDate, this.genres, state);
    }

}
