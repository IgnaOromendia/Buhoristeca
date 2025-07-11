package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.dto.BookCopiesAmountDto;
import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.dto.BookRequestDto;
import com.itpatagonia.Buhoristeca.entities.*;
import com.itpatagonia.Buhoristeca.projections.BookCopiesAmountProjection;
import com.itpatagonia.Buhoristeca.repositories.*;

import com.itpatagonia.Buhoristeca.util.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private RoleService roleService;

    public List<BookDto> getAllLoanedBooksByRole(Integer idRole, LocalDate startDate, LocalDate endDate) {
        DateValidator.assertEndDateIsAfterStartDate(startDate, endDate);
        roleService.assertRoleExists(idRole);

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
        DateValidator.assertEndDateIsAfterStartDate(startDate, endDate);

        List<Book> loanedBooks = bookRepository.findBooksWithNoLoansBetween(startDate, endDate);

        if (loanedBooks == null)
            throw new RuntimeException("Error en la búsqueda de libros nunca prestados entre fechas");

        return loanedBooks.stream().map(Book::convertToBookDto).toList();
    }

    public List<BookDto> getBookLoanedToClientWithId(Integer idClient) {
        clientService.assertClientIsRegistered(idClient);

        List<Book> books = bookRepository.findBooksLoanedToClientWithId(idClient);

        if (books == null)
            throw new RuntimeException("Error en la búsqueda de libros para el cliente con dni " + idClient);

        return books.stream().map(Book::convertToBookDto).toList();
    }

    public BookDto registerNewBook(BookRequestDto bookRequestDto) {
        assertBookIsNotRegistered(bookRequestDto);

        Author bookAuthor       = authorService.getAuthorById(bookRequestDto.getIdAuthor());
        Publisher bookPublisher = publisherService.getPublisherById(bookRequestDto.getIdPublisher());
        Language bookLanguage   = languageService.getLanguageById(bookRequestDto.getIdLanguage());
        Set<Genre> bookGenres   = genreService.getGenresByIds(bookRequestDto.getGenresIds());

        Book savedBook = bookRepository.save(bookRequestDto.convertToBook(bookAuthor, bookLanguage, bookPublisher, bookGenres));
        return savedBook.convertToBookDto();
    }

    // Asserts

    public void assertBookExists(Integer idBook) {
        if (bookRepository.findById(idBook).isEmpty()) throw new RuntimeException("El libro con id " + idBook + " no existe");
    }

    private void assertBookIsNotRegistered(BookRequestDto bookRequestDto) {
        if (bookRepository.findByAttribute(
                bookRequestDto.getTitle(),
                bookRequestDto.getIdPublisher(),
                bookRequestDto.getIdAuthor(),
                bookRequestDto.getIdLanguage()).isPresent()) throw new RuntimeException("El libro ya existe");
    }

}
