package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.entities.BookCopy;
import com.itpatagonia.Buhoristeca.repositories.BookCopyRepository;
import com.itpatagonia.Buhoristeca.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookCopyService {

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookService bookService;

    public BookCopy getAvailableCopy(Integer idBook) {
        bookService.assertBookExists(idBook);
        return assertThereIsABookCopyAvailable(idBook);
    }

    public void updateStateToNotAvailableOfCopyWithId(Integer idBook, Integer idBookCopy) {
        bookCopyRepository.updateStateToNotAvailableOfCopyWithId(idBook, idBookCopy);
    }

    public void updateStateToAvailableOf(Integer idBook, Integer idBookCopy) {
        bookCopyRepository.updateStateToAvailableOf(idBook, idBookCopy);
    }

    // Asserts

    private BookCopy assertThereIsABookCopyAvailable(Integer idBook) {
        BookCopy bookCopy = bookCopyRepository.findAvailableCopyWithIdBook(idBook);

        if (bookCopy == null)
            throw new RuntimeException("No hya copias disponibles del libro con id " + idBook);

        return bookCopy;
    }
}
