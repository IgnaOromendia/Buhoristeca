package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.entities.BookCopy;
import com.itpatagonia.Buhoristeca.repositories.BookCopyRepository;
import com.itpatagonia.Buhoristeca.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCopyService {

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookService bookService;

    public BookCopy getAvailableCopy(Integer idBook) {
        bookService.assertBookExists(idBook);
        return bookCopyRepository.findAvailableCopyWithIdBook(idBook);
    }

    public void updateStateToNotAvailableOfCopyWithId(Integer idBook, Integer idBookCopy) {
        bookCopyRepository.updateStateToNotAvailableOfCopyWithId(idBook, idBookCopy);
    }

    public void updateStateToAvailableOf(Integer idBook, Integer idBookCopy) {
        bookCopyRepository.updateStateToAvailableOf(idBook, idBookCopy);
    }
}
