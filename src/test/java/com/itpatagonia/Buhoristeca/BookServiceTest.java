package com.itpatagonia.Buhoristeca;

import com.itpatagonia.Buhoristeca.dto.BookCopiesAmountDto;
import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.dto.BookRequestDto;
import com.itpatagonia.Buhoristeca.entities.*;
import com.itpatagonia.Buhoristeca.exceptions.ClientNotFoundException;
import com.itpatagonia.Buhoristeca.exceptions.EndDateIsAfterStartDateException;
import com.itpatagonia.Buhoristeca.projections.BookCopiesAmountProjection;
import com.itpatagonia.Buhoristeca.repositories.BookRepository;
import com.itpatagonia.Buhoristeca.repositories.ClientRepository;
import com.itpatagonia.Buhoristeca.services.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private AuthorService authorService;

    @Mock
    private PublisherService publisherService;

    @Mock
    private LanguageService languageService;

    @Mock
    private GenreService genreService;

    @Mock
    private ClientService clientService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private BookService bookService;

    @Test
    void test01IfThereAreNoBooksLoanedAllBooksLoanedByRoleIsEmpty() {
        doNothing().when(roleService).assertRoleExists(1);
        when(bookRepository.findBooksWithLoansByRole(1, LocalDate.now(), LocalDate.now()))
                .thenReturn(new ArrayList<>());

        List<BookDto> loanedBooks = bookService.getAllLoanedBooksByRole(1, LocalDate.now(), LocalDate.now());

        assertTrue(loanedBooks.isEmpty());
    }

    @Test
    void test02ToGetAllLoanedBooksDatesMustBeValid() {
        assertThrows(EndDateIsAfterStartDateException.class, () -> bookService.getAllLoanedBooksByRole(1, LocalDate.now(), LocalDate.now().minusDays(4)));
    }

    @Test
    void test03WhenABookIsLoanItCanBeTracked() {
        Book book = mock(Book.class);

        doNothing().when(roleService).assertRoleExists(1);
        when(bookRepository.findBooksWithLoansByRole(1, LocalDate.now(), LocalDate.now()))
                .thenReturn(List.of(book));

        List<BookDto> loanedBooks = bookService.getAllLoanedBooksByRole(1, LocalDate.now(), LocalDate.now());

        assertFalse(loanedBooks.isEmpty());
        assertTrue(loanedBooks.contains(book.convertToBookDto()));
    }

    @Test
    void test04IfThereAreNoBooksThereAreNoCopies() {
        when(bookRepository.findAllCopiesAmount()).thenReturn(new ArrayList<>());

        List<BookCopiesAmountDto> bookCopies = bookService.getAllCopiesAmount();

        assertTrue(bookCopies.isEmpty());
    }

    @Test
    void test05CopiesAmountAreExactlyTheAmountOfCopies() {
        BookCopiesAmountProjection projection = mock(BookCopiesAmountProjection.class);
        when(bookRepository.findAllCopiesAmount()).thenReturn(List.of(projection));
        when(bookRepository.existsById(1)).thenReturn(true);
        when(bookRepository.findCopiesAmountByIdBook(1)).thenReturn(projection);

        List<BookCopiesAmountDto> bookCopies = bookService.getAllCopiesAmount();
        BookCopiesAmountDto bookCopy = bookService.getAllCopiesAmountByBook(1);

        assertFalse(bookCopies.isEmpty());
        assertTrue(bookCopies.contains(bookCopy));
    }

    @Test
    void test06IfThereAreNoBooksAreZeroBooksWithNoLoans() {
        when(bookRepository.findBooksWithNoLoans()).thenReturn(new ArrayList<>());

        List<BookDto> noLoanedBooks = bookService.getAllBooksWithNoLoans();

        assertTrue(noLoanedBooks.isEmpty());
    }

    @Test
    void test07IfThereAreNotBooksLoanedThereAreBooksWithNoLoans() {
        Book book1 = mock(Book.class);
        Book book2 = mock(Book.class);

        when(bookRepository.findBooksWithNoLoans()).thenReturn(List.of(book1, book2));

        List<BookDto> noLoanedBooks = bookService.getAllBooksWithNoLoans();

        assertFalse(noLoanedBooks.isEmpty());
        assertTrue(noLoanedBooks.contains(book1.convertToBookDto()));
        assertTrue(noLoanedBooks.contains(book2.convertToBookDto()));
    }

    @Test
    void test08DatesMustBeValidWhenAskingForBookWithNoLoans() {
        assertThrows(EndDateIsAfterStartDateException.class, () -> bookService.getAllBooksWithNoLoansBetween(LocalDate.now(), LocalDate.now().minusYears(1)));
    }

    @Test
    void test09OnlyBooksWithNoLoanBetweenDatesAppears() {
        Book bookLoaned = mock(Book.class);
        Book bookNoLoaned = mock(Book.class);
        BookDto bookLoanedDto = mock(BookDto.class);
        BookDto bookNoLoanedDto = mock(BookDto.class);

        when(bookLoaned.convertToBookDto()).thenReturn(bookLoanedDto);
        when(bookNoLoaned.convertToBookDto()).thenReturn(bookNoLoanedDto);
        doNothing().when(roleService).assertRoleExists(1);
        when(bookRepository.findBooksWithLoansByRole(1, LocalDate.now(), LocalDate.now())).thenReturn(List.of(bookLoaned));
        when(bookRepository.findBooksWithNoLoansBetween(LocalDate.now(), LocalDate.now())).thenReturn(List.of(bookNoLoaned));

        List<BookDto> loanedBooks = bookService.getAllLoanedBooksByRole(1, LocalDate.now(), LocalDate.now());
        List<BookDto> noLoanedBooks = bookService.getAllBooksWithNoLoansBetween(LocalDate.now(), LocalDate.now());

        List<BookDto> intersection = new ArrayList<>(loanedBooks);

        intersection.retainAll(noLoanedBooks);

        assertTrue(intersection.isEmpty());
    }

    @Test
    void test10IfThereNoLoanedBooksAClientCanNotHaveLoans() {
        doNothing().when(clientService).assertClientIsRegistered(1);
        when(bookRepository.findBooksWithLoansByRole(1,LocalDate.now(), LocalDate.now())).thenReturn(new ArrayList<>());
        when(bookRepository.findBooksLoanedToClientWithId(1)).thenReturn(new ArrayList<>());

        List<BookDto> booksLoaned = bookService.getAllLoanedBooksByRole(1, LocalDate.now(), LocalDate.now());
        List<BookDto> booksLoanedToClient = bookService.getBookLoanedToClientWithId(1);

        assertTrue(booksLoaned.isEmpty());
        assertTrue(booksLoanedToClient.isEmpty());
    }

    @Test
    void test11ThereAreNoActiveBooksWhenThereAreZeroBooksRegistered() {
        when(bookRepository.findActive()).thenReturn(new ArrayList<>());
        assertTrue(bookService.getActiveBooks().isEmpty());
    }

    @Test
    void test12ThereAreActiveBooksWhenThereAreBooksRegistered() {
        BookRequestDto bookRequestDto = mock(BookRequestDto.class);
        Author author = mock(Author.class);
        Publisher publisher = mock(Publisher.class);
        Language language = mock(Language.class);
        Set<Genre> genres = new HashSet<>();
        Book book = mock(Book.class);
        BookDto bookDto = mock(BookDto.class);

        when(bookRequestDto.convertToBook(author, language, publisher, genres)).thenReturn(book);

        when(bookRepository.findActive()).thenReturn(List.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(book.convertToBookDto()).thenReturn(bookDto);
        when(authorService.getAuthorById(anyInt())).thenReturn(author);
        when(publisherService.getPublisherById(anyInt())).thenReturn(publisher);
        when(languageService.getLanguageById(anyInt())).thenReturn(language);
        when(genreService.getGenresByIds(anySet())).thenReturn(genres);
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));

        BookDto bookSaved = bookService.registerNewBook(bookRequestDto);
        List<BookDto> activeBooks = bookService.getActiveBooks();

        assertEquals(bookSaved, bookService.getBookById(anyInt()).convertToBookDto());
        assertFalse(activeBooks.isEmpty());
        assertTrue(activeBooks.contains(bookSaved));

    }

    @Test
    void test13BookCanBeRemovedAndActivated() {
        Book book = spy(new Book());
        book.setActive();

        doNothing().when(entityManager).refresh(any());
        when(bookRepository.existsById(anyInt())).thenReturn(true);
        when(bookRepository.findById(anyInt())).thenReturn(Optional.of(book));

        assertNotNull(book);
        assertEquals(book, bookService.getBookById(anyInt()));
        assertTrue(book.isActive());

        doAnswer(inv -> {
            book.setInactive();
            return null;
        }).when(bookRepository).updateStatus(anyInt(), anyInt());

        bookService.removeBookWithId(anyInt());

        assertFalse(book.isActive());

        doAnswer(inv -> {
            book.setActive();
            return null;
        }).when(bookRepository).updateStatus(anyInt(), anyInt());

        bookService.activateBookWithId(anyInt());

        assertTrue(bookService.getBookById(anyInt()).isActive());
    }
}
