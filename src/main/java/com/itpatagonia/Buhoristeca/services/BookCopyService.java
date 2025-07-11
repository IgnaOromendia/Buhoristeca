package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.dto.BookCopyDto;
import com.itpatagonia.Buhoristeca.entities.Book;
import com.itpatagonia.Buhoristeca.entities.BookCopy;
import com.itpatagonia.Buhoristeca.entities.BookState;
import com.itpatagonia.Buhoristeca.repositories.BookCopyRepository;
import com.itpatagonia.Buhoristeca.repositories.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookCopyService {

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookStateService bookStateService;

    @PersistenceContext
    private EntityManager entityManager;

    private final Integer idAvailableState      = 1;
    private final Integer idNotAvailableState   = 2;

    public BookCopy getAvailableCopy(Integer idBook) {
        bookService.assertBookExists(idBook);
        return assertThereIsABookCopyAvailable(idBook);
    }

    public void updateStateToNotAvailableOfCopyWithId(Integer idBook, Integer idBookCopy) {
        updateStateOf(idBook, idBookCopy, idNotAvailableState);
    }

    public void updateStateToAvailableOf(Integer idBook, Integer idBookCopy) {
        updateStateOf(idBook, idBookCopy, idAvailableState);
    }

    @Transactional
    public BookCopyDto updateStateOf(Integer idBook, Integer idBookCopy, Integer idState) {
        bookService.assertBookExists(idBook);
        assertBookCopyExists(idBook, idBookCopy);
        bookStateService.assertStateExists(idState);

        bookCopyRepository.updateState(idBook, idBookCopy, idState);

        BookCopy updatedBookCopy = bookCopyRepository.findByBookCopyId(idBook, idBookCopy);

        // Si no hacemos esto deuvleve los datos cacheados, entonces parece que le estado no cambia
        entityManager.refresh(updatedBookCopy);

        return updatedBookCopy.convertToDto();
    }

    public BookCopyDto registerNewBookCopies(Integer idBook) {
        Book book = bookService.getBookById(idBook);

        Integer nextCopyNumber = bookCopyRepository.getLastIdBookCopy(idBook) + 1;

        BookCopy bookCopy = new BookCopy(book, bookStateService.getAvailableState(), nextCopyNumber);

        BookCopy savedBookcopy = bookCopyRepository.save(bookCopy);

        return savedBookcopy.convertToDto();
    }

    // Asserts

    private void assertBookCopyExists(Integer idBook, Integer idBookCopy) {
        if (bookCopyRepository.findByBookCopyId(idBook, idBookCopy) == null) throw new RuntimeException("La copia con id " + idBookCopy + " del libro con id " + idBook + " no existe");
    }

    private BookCopy assertThereIsABookCopyAvailable(Integer idBook) {
        BookCopy bookCopy = bookCopyRepository.findAvailableCopyWithIdBook(idBook);

        if (bookCopy == null)
            throw new RuntimeException("No hya copias disponibles del libro con id " + idBook);

        return bookCopy;
    }



}
