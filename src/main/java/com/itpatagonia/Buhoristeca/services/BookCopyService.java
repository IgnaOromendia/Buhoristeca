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
    private BookRepository bookRepository;

    public BookCopy getAvailableCopy(Integer idBook) {
        assertBookExists(idBook);
        return bookCopyRepository.findAvailableCopyWithIdBook(idBook);
    }

    private void assertBookExists(Integer idBook) {
        if (bookRepository.findById(idBook).isEmpty())
            throw new RuntimeException("El libro con id " + idBook + " no existe");
    }

    public void updateStateToNotAvailableOfCopyWithId(Integer idBook, Integer idBookCopy) {
        bookCopyRepository.updateStateToNotAvailableOfCopyWithId(idBook, idBookCopy);
    }
}
