package com.itpatagonia.Buhoristeca.entities;

import com.itpatagonia.Buhoristeca.dto.BookDto;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBook", nullable = false)
    private Integer idBook;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "idAuthor", nullable = false)
    private Author author;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "publicationDate", nullable = false)
    private LocalDate publicationDate;

    @ManyToOne
    @JoinColumn(name = "idPublisher", nullable = false)
    private Publisher publisher;

    @ManyToOne
    @JoinColumn(name = "idLanguage", nullable = false)
    private Language language;

    @Column(name = "isActive", nullable = false)
    private Integer isActive;

    @ManyToMany
    @JoinTable(
            name = "bookGenre",
            joinColumns = @JoinColumn(name = "idBook"),
            inverseJoinColumns = @JoinColumn(name = "idGenre")
    )
    private Set<Genre> genres;

    public BookDto convertToBookDto() {
        return new BookDto(title, description, publicationDate, genres);
    }
}
