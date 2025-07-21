package com.itpatagonia.Buhoristeca;

import com.itpatagonia.Buhoristeca.controllers.BookController;
import com.itpatagonia.Buhoristeca.dto.BookCopiesAmountDto;
import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.dto.BookRequestDto;
import com.itpatagonia.Buhoristeca.services.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookControllerMockitoTest {
    
    @Mock
    private BookService bookService;
    
    @InjectMocks
    private BookController bookController;
    
    private BookDto bookDto;
    private BookCopiesAmountDto bookCopiesAmountDto;
    
    private final LocalDate startDate = LocalDate.now();
    private final LocalDate endDate = LocalDate.now().plusDays(10);
    
    @BeforeEach
    void setUp() {
        bookDto = mock(BookDto.class);
        bookCopiesAmountDto = mock(BookCopiesAmountDto.class);
    }
    
    @Test
    void getLoanedBook_validRequest_returnsOk() {
        when(bookService.getAllLoanedBooksByRole(1, startDate, endDate)).thenReturn(List.of(bookDto));

        ResponseEntity<List<BookDto>> response = bookController.getLoanedBook(1, startDate, endDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(bookDto), response.getBody());
    }

    @Test
    void getAmountOfCopies_validRequest_returnsOk() {
        when(bookService.getAllCopiesAmount()).thenReturn(List.of(bookCopiesAmountDto));

        ResponseEntity<List<BookCopiesAmountDto>> response = bookController.getAmountOfCopies();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(bookCopiesAmountDto), response.getBody());
    }

    @Test
    void getAmountOfCopiesByBook_validRequest_returnsOk() {
        when(bookService.getAllCopiesAmountByBook(1)).thenReturn(bookCopiesAmountDto);

        ResponseEntity<BookCopiesAmountDto> response = bookController.getAmountOfCopiesByBook(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookCopiesAmountDto, response.getBody());
    }

    @Test
    void getAllBooksWithNoLoans_validRequest_returnsOk() {
        when(bookService.getAllBooksWithNoLoans()).thenReturn(List.of(bookDto));

        ResponseEntity<List<BookDto>> response = bookController.getAllBooksWithNoLoans();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(bookDto), response.getBody());
    }

    @Test
    void getAllBooksWithNoLoansBetweenDates_validRequest_returnsOk() {
        when(bookService.getAllBooksWithNoLoansBetween(startDate, endDate)).thenReturn(List.of(bookDto));

        ResponseEntity<List<BookDto>> response = bookController.getAllBooksWithNoLoansBetweenDates(startDate, endDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(bookDto), response.getBody());
    }

    @Test
    void getBookLoanedToClientWithId_validRequest_returnsOk() {
        when(bookService.getBookLoanedToClientWithId(1)).thenReturn(List.of(bookDto));

        ResponseEntity<List<BookDto>> response = bookController.getBookLoanedToClientWithId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(bookDto), response.getBody());
    }

    @Test
    void registerNewBook_validRequest_returnsOk() {
        BookRequestDto request = mock(BookRequestDto.class);

        when(bookService.registerNewBook(request)).thenReturn(bookDto);

        ResponseEntity<BookDto> response = bookController.registerNewBook(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDto, response.getBody());
    }

    @Test
    void removeBookWithId_validRequest_returnsOk() {
        when(bookService.removeBookWithId(1)).thenReturn(bookDto);

        ResponseEntity<BookDto> response = bookController.removeBookWithId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDto, response.getBody());
    }

    @Test
    void activateBookWithId_validRequest_returnsOk() {
        when(bookService.activateBookWithId(1)).thenReturn(bookDto);

        ResponseEntity<BookDto> response = bookController.activateBookWithId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bookDto, response.getBody());
    }

    @Test
    void getActiveBooks_validRequest_returnsOk() {
        when(bookService.getActiveBooks()).thenReturn(List.of(bookDto));

        ResponseEntity<List<BookDto>> response = bookController.getActiveBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(bookDto), response.getBody());
    }

}

