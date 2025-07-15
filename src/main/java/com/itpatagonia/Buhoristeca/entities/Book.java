package com.itpatagonia.Buhoristeca.entities;

import com.itpatagonia.Buhoristeca.dto.BookCopyDto;
import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.exceptions.PDFFileException;
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

    public Book(String title, Author author, String description, LocalDate publicationDate, Publisher publisher, Language language, Set<Genre> genres) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.language = language;
        this.genres = genres;
        this.isActive = 1;
    }

    public Book() {}

    public BookDto convertToBookDto() {
        return new BookDto(this.title, this.description, this.publicationDate, this.genres, this.isActive);
    }

    public void addTitleTo(StringBuilder bookTitle) {
        bookTitle.append(this.title);
    }

    public BookCopyId getBookCopyId(Integer idBookCopy) {
        return new BookCopyId(this.idBook, idBookCopy);
    }

    public PDFBookId getPDFBookId(Integer idPdfBook) {
        return new PDFBookId(this.idBook, idPdfBook);
    }

    public boolean isActive() {
        return this.isActive == 1;
    }

    public void throwExceptionUploadingPDF(String message) {
        throw new PDFFileException(this.idBook, message);
    }

    public void addAuthorTo(StringBuilder bookAuthor) {
        this.author.addNameTo(bookAuthor);
    }
}
