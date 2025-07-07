package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.dto.BookCopiesAmountDto;
import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.dto.BookRequestDto;
import com.itpatagonia.Buhoristeca.entities.*;
import com.itpatagonia.Buhoristeca.projections.BookCopiesAmountProjection;
import com.itpatagonia.Buhoristeca.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private GenreRepository genreRepository;


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

    public BookDto registerNewBook(BookRequestDto bookRequestDto) {
        assertBookIsNotRegistered(bookRequestDto);

        Author bookAuthor       = assertAuthorExists(bookRequestDto.getIdAuthor());
        Publisher bookPublisher = assertPublisherExists(bookRequestDto.getIdPublisher());
        Language bookLanguage   = assertLanguageExists(bookRequestDto.getIdLanguage());
        Set<Genre> bookGenres   = assertAllGenresExists(bookRequestDto.getGenresIds());

        Book savedBook = bookRepository.save(bookRequestDto.convertToBook(bookAuthor, bookLanguage, bookPublisher, bookGenres));
        return savedBook.convertToBookDto();
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

    private Author assertAuthorExists(Integer idAuthor) {
        Optional<Author> author = authorRepository.findById(idAuthor);

        if (author.isEmpty()) throw new RuntimeException("El autor con id " + idAuthor + " no existe");

        return author.get();
    }

    private Publisher assertPublisherExists(Integer idPublisher) {
        Optional<Publisher> publisher = publisherRepository.findById(idPublisher);

        if (publisher.isEmpty()) throw new RuntimeException("La editorial con id " + idPublisher + " no existe");

        return publisher.get();
    }

    private Language assertLanguageExists(Integer idLanguage) {
        Optional<Language> language = languageRepository.findById(idLanguage);

        if (language.isEmpty()) throw new RuntimeException("El idioma con id " + idLanguage + " no existe");

        return language.get();
    }

    private void assertBookIsNotRegistered(BookRequestDto bookRequestDto) {
        if (bookRepository.findByAttribute(
                bookRequestDto.getTitle(),
                bookRequestDto.getIdPublisher(),
                bookRequestDto.getIdAuthor(),
                bookRequestDto.getIdLanguage()).isPresent()) throw new RuntimeException("El libro ya existe");
    }

    private Set<Genre> assertAllGenresExists(Set<Integer> genresIds) {
        if (genresIds.isEmpty()) return new HashSet<>();

        List<Genre> genres = genreRepository.findAllById(genresIds);

        if (genres.isEmpty()) throw new RuntimeException("Error en los géneros");

        return new HashSet<>(genres);
    }


}
