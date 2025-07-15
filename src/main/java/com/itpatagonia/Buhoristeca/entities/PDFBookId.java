package com.itpatagonia.Buhoristeca.entities;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PDFBookId implements Serializable {

    private Integer idPdfBook;
    private Integer idBook;

    public PDFBookId() {}

    public PDFBookId(Integer idBook, Integer idPdfBook) {
        this.idPdfBook = idPdfBook;
        this.idBook = idBook;
    }
}
