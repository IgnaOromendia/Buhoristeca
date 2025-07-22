package com.itpatagonia.Buhoristeca.entities;

import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.dto.PDFBookDto;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Entity
@Table(name = "pdfBook")
public class PDFBook {

    @EmbeddedId
    private PDFBookId pdfBookId;

    @ManyToOne
    @MapsId("idBook")
    @JoinColumn(name = "idBook", nullable = false)
    private Book book;

    @Lob
    @Column(name = "pdfFile", nullable = false)
    private byte[] file;

    public PDFBook() {}

    public PDFBook(Book book, MultipartFile file, Integer idPdfBook) {
        try {
            this.book = book;
            this.file = file.getBytes();
            this.pdfBookId = this.book.getPDFBookId(idPdfBook);
        } catch (IOException e) {
            book.throwExceptionUploadingPDF(e.getMessage());
        }
    }

    public PDFBookDto convertToDto() {
        StringBuilder bookTitle = new StringBuilder();
        StringBuilder bookAuthor = new StringBuilder();

        this.book.addTitleTo(bookTitle);
        this.book.addAuthorTo(bookAuthor);

        return new PDFBookDto(bookTitle.toString(), bookAuthor.toString(), this.file);
    }

    public BookDto convertToBookDto() {
        return this.book.convertToBookDto();
    }

    public byte[] toFile() {
        return this.file;
    }
}
