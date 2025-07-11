package com.itpatagonia.Buhoristeca.entities;

import com.itpatagonia.Buhoristeca.services.BookCopyService;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class BookCopyId {

    private Integer idBook;
    private Integer idBookCopy;

    public BookCopyId() {}

    public BookCopyId(Integer idBook) {
        this.idBook = idBook;
    }

    public BookCopyId(Integer idBook, Integer idBookCopy) {
        this.idBook = idBook;
        this.idBookCopy = idBookCopy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCopyId anotherId)) return false;
        return Objects.equals(this.idBook, anotherId.idBook) && Objects.equals(this.idBookCopy, anotherId.idBookCopy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idBook, this.idBookCopy);
    }

    public void addCopyNumberTo(StringBuilder copyNumber) {
        copyNumber.append(this.idBookCopy);
    }

    public void updateStateToNotAvailableOn(BookCopyService bookCopyService) {
        bookCopyService.updateStateToNotAvailableOfCopyWithId(this.idBook, this.idBookCopy);
    }
}
