package com.itpatagonia.Buhoristeca.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "bookCopy")
public class BookCopy {

    @EmbeddedId
    private BookCopyId id;

    // Como la clave es compuesta (idBook, idBookCopy) tengo que indicarle cual es la de Book
    @ManyToOne
    @MapsId("idBook")
    @JoinColumn(name = "idBook", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "idState", nullable = false)
    private BookState state;

}
