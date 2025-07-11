package com.itpatagonia.Buhoristeca.entities;

import com.itpatagonia.Buhoristeca.dto.BookCopyDto;
import com.itpatagonia.Buhoristeca.services.BookCopyService;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bookCopy")
public class BookCopy {

    @EmbeddedId
    private BookCopyId bookCopyId;

    // Como la clave es compuesta (idBook, idBookCopy) tengo que indicarle cual es la de Book
    @ManyToOne
    @MapsId("idBook")
    @JoinColumn(name = "idBook", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "idState", nullable = false)
    private BookState state;

    public void updateStateToNotAvailableOn(BookCopyService bookCopyService) {
        bookCopyId.updateStateToNotAvailableOn(bookCopyService);
    }

    public void addBookInformationTo(StringBuilder bookTitle, StringBuilder copyNumber) {
        this.book.addTitleTo(bookTitle);
        this.bookCopyId.addCopyNumberTo(copyNumber);
    }

    public BookCopy() {}

    public BookCopy(Book book, BookState bookState, Integer idBookCopy) {
        this.book = book;
        this.state = bookState;
        this.bookCopyId = this.book.getBookCopyId(idBookCopy);
    }

    public BookCopyDto convertToDto() {
        return this.book.convertToBookDto().converToBookCopyDto(this.state);
    }
}
