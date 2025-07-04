package com.itpatagonia.Buhoristeca.entities;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class BookCopyId {

    private final Integer idBook;
    private final Integer idBookCopy;

    public BookCopyId(Integer idBook, Integer idBookCopy) {
        this.idBook = idBook;
        this.idBookCopy = idBookCopy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCopyId)) return false;
        BookCopyId anotherId = (BookCopyId) o;
        return Objects.equals(idBook, anotherId.idBook) && Objects.equals(idBookCopy, anotherId.idBookCopy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idBook, idBookCopy);
    }

}
