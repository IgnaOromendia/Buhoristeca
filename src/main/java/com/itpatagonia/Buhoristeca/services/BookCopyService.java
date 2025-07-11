package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.dto.BookCopyDto;
import com.itpatagonia.Buhoristeca.entities.Book;
import com.itpatagonia.Buhoristeca.entities.BookCopy;
import com.itpatagonia.Buhoristeca.entities.BookState;
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

    @Autowired
    private BookStateService bookStateService;

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

    public BookCopyDto registerNewBookCopies(Integer idBook) {
        Book book = bookService.getBookById(idBook);

        Integer nextCopyNumber = bookCopyRepository.getLastIdBookCopy(idBook) + 1;

        BookCopy bookCopy = new BookCopy(book, bookStateService.getAvailableState(), nextCopyNumber);

        BookCopy savedBookcopy = bookCopyRepository.save(bookCopy);

        return savedBookcopy.convertToDto();
    }

    // Asserts

    private BookCopy assertThereIsABookCopyAvailable(Integer idBook) {
        BookCopy bookCopy = bookCopyRepository.findAvailableCopyWithIdBook(idBook);

        if (bookCopy == null)
            throw new RuntimeException("No hya copias disponibles del libro con id " + idBook);

        return bookCopy;
    }


}
