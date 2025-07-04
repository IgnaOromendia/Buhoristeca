package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.dto.BookCopiesAmountDto;
import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.entities.Book;
import com.itpatagonia.Buhoristeca.projections.BookCopiesAmountProjection;
import com.itpatagonia.Buhoristeca.repositories.BookRepository;

import com.itpatagonia.Buhoristeca.repositories.ClientRepository;
import com.itpatagonia.Buhoristeca.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClientRepository clientRepository;

    public List<BookDto> getAllLoanedBooksByRole(Integer idRole, LocalDate startDate, LocalDate endDate) {
        assertEndDateIsAfterStartDate(startDate, endDate);
        assertRoleExists(idRole);

        List<Book> loanedBooks = bookRepository.findBooksWithLoansByRole(idRole, startDate, endDate);

        if (loanedBooks == null)
            throw new RuntimeException("Error en la búsqueda de libros prestados");

        return loanedBooks.stream().map(Book::convertToBookDto).toList();
    }

    public List<BookCopiesAmountDto> getAllCopiesAmount() {
        List<BookCopiesAmountProjection> bookCopiesAmount = bookRepository.findAllCopiesAmount();

        if (bookCopiesAmount == null)
            throw new RuntimeException("Error en la búsqueda de copias de todos los libros");

        return bookCopiesAmount.stream().map(BookCopiesAmountDto::new).toList();
    }

    public BookCopiesAmountDto getAllCopiesAmountByBook(Integer idBook) {
        assertBookExists(idBook);

        BookCopiesAmountProjection bookCopiesAmountProjection = bookRepository.findCopiesAmountByIdBook(idBook);

        if (bookCopiesAmountProjection == null)
            throw new RuntimeException("Error en la búsqueda de copias de todos los libros");

        return new BookCopiesAmountDto(bookCopiesAmountProjection);
    }

    public List<BookDto> getAllBooksWithNoLoans() {
        List<Book> loanedBooks = bookRepository.findBooksWithNoLoans();

        if (loanedBooks == null)
            throw new RuntimeException("Error en la búsqueda de libros nunca prestados");

        return loanedBooks.stream().map(Book::convertToBookDto).toList();
    }

    public List<BookDto> getAllBooksWithNoLoansBetween(LocalDate startDate, LocalDate endDate) {
        assertEndDateIsAfterStartDate(startDate, endDate);

        List<Book> loanedBooks = bookRepository.findBooksWithNoLoansBetween(startDate, endDate);

        if (loanedBooks == null)
            throw new RuntimeException("Error en la búsqueda de libros nunca prestados entre fechas");

        return loanedBooks.stream().map(Book::convertToBookDto).toList();
    }

    public List<BookDto> getBookLoanedToClientWithId(Integer idClient) {
        assertClientIsRegistered(idClient);

        List<Book> books = bookRepository.findBooksLoanedToClientWithId(idClient);

        if (books == null)
            throw new RuntimeException("Error en la búsqueda de libros para el cliente con dni " + idClient);

        return books.stream().map(Book::convertToBookDto).toList();
    }

    // Asserts

    private void assertEndDateIsAfterStartDate(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) throw new RuntimeException("Período de tiempo inválido");
    }

    private void assertRoleExists(Integer idRole) {
        if (roleRepository.findById(idRole).isEmpty()) throw new RuntimeException("El rol buscado no existe");
    }

    private void assertBookExists(Integer idBook) {
        if (bookRepository.findById(idBook).isEmpty()) throw new RuntimeException("El libro con id " + idBook + " no existe");
    }

    private void assertClientIsRegistered(Integer idClient) {
        if (clientRepository.findById(idClient).isEmpty()) throw new RuntimeException("El cliente con id " + idClient + " no está registrado");
    }


}
