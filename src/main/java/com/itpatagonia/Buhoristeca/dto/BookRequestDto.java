package com.itpatagonia.Buhoristeca.dto;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itpatagonia.Buhoristeca.entities.*;

import java.time.LocalDate;
import java.util.Set;

public class BookRequestDto {

    private final String title;
    private final Integer idAuthor;
    private final String description;
    private final LocalDate publicationDate;
    private final Integer idPublisher;
    private final Integer idLanguage;
    private final Set<Integer> genresIds;

    public BookRequestDto(String title, Integer idAuthor, String description, LocalDate publicationDate, Integer idPublisher, Integer idLanguage, Set<Integer> genres) {
        this.title = title;
        this.idAuthor = idAuthor;
        this.description = description;
        this.publicationDate = publicationDate;
        this.idPublisher = idPublisher;
        this.idLanguage = idLanguage;
        this.genresIds = genres;
    }

    public String getTitle() {
        return this.title;
    }

    public Integer getIdAuthor() {
        return this.idAuthor;
    }

    public LocalDate getPublicationDate() {
        return this.publicationDate;
    }

    public Integer getIdPublisher() {
        return this.idPublisher;
    }

    public Integer getIdLanguage() {
        return this.idLanguage;
    }

    public Set<Integer> getGenresIds() {
        return this.genresIds;
    }

    public Book convertToBook(Author bookAuthor, Language bookLanguage, Publisher bookPublisher, Set<Genre> genres) {
        return new Book(this.title, bookAuthor, this.description, this.publicationDate, bookPublisher, bookLanguage, genres);
    }
}
