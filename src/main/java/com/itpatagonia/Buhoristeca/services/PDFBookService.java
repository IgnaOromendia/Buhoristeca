package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.dto.PDFBookDto;
import com.itpatagonia.Buhoristeca.entities.Book;
import com.itpatagonia.Buhoristeca.entities.PDFBook;
import com.itpatagonia.Buhoristeca.entities.PDFBookId;
import com.itpatagonia.Buhoristeca.exceptions.PDFBookNotFoundException;
import com.itpatagonia.Buhoristeca.repositories.PDFBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PDFBookService {

    @Autowired
    private PDFBookRepository pdfBookRepository;

    @Autowired
    private BookService bookService;

    public BookDto uploadPDFBook(Integer idBook, MultipartFile file) {
        Book book = bookService.getBookById(idBook);
        Integer lastPDFId = pdfBookRepository.getLastPDFId(idBook);
        Integer nextPDFId = (lastPDFId == null ? 0 : lastPDFId) + 1;
        PDFBook savedPDFBook = pdfBookRepository.save(new PDFBook(book, file, nextPDFId));
        return savedPDFBook.convertToBookDto();
    }

    public PDFBookDto downloadPDFBookWithId(Integer idBook, Integer idPdfBook) {
        bookService.assertBookExists(idBook);
        PDFBook pdfBook = pdfBookRepository.findById(new PDFBookId(idBook, idPdfBook)).orElseThrow(() -> new PDFBookNotFoundException(idBook, idPdfBook));
        return pdfBook.convertToDto();
    }


}
