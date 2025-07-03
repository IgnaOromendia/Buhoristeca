package com.itpatagonia.Buhoristeca.entities;

import com.itpatagonia.Buhoristeca.dto.BookDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "idBook", nullable = false)
    private Integer idBook;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "idAuthor", nullable = false)
    private Integer idAuthor;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "publicationDate", nullable = false)
    private LocalDate publicationDate;

    @Column(name = "idPublisher", nullable = false)
    private Integer idPublisher;

    @Column(name = "idLanguage", nullable = false)
    private Integer idLanguage;

    @Column(name = "isActive", nullable = false)
    private Integer isActive;

    public BookDto convertToBookDto() {
        return new BookDto(title, description, publicationDate);
    }
}
