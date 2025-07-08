package com.itpatagonia.Buhoristeca.entities;

import com.itpatagonia.Buhoristeca.services.BookCopyService;
import jakarta.persistence.*;

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
}
