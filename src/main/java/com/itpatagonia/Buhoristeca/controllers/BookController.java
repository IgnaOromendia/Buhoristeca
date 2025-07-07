package com.itpatagonia.Buhoristeca.controllers;

import com.itpatagonia.Buhoristeca.dto.BookCopiesAmountDto;
import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.dto.BookRequestDto;
import com.itpatagonia.Buhoristeca.services.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/user/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/loaned/role/{idRole}")
    public ResponseEntity<List<BookDto>> getLoanedBook(
            @PathVariable Integer idRole,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(bookService.getAllLoanedBooksByRole(idRole, startDate, endDate));
    }

    @GetMapping("/copies")
    public ResponseEntity<List<BookCopiesAmountDto>> getAmountOfCopies() {
        return ResponseEntity.ok(bookService.getAllCopiesAmount());
    }

    @GetMapping("/copies/{idBook}")
    public ResponseEntity<BookCopiesAmountDto> getAmountOfCopiesByBook(
            @PathVariable Integer idBook
    ) {
        return ResponseEntity.ok(bookService.getAllCopiesAmountByBook(idBook));
    }

    @GetMapping("/loaned/never")
    public ResponseEntity<List<BookDto>> getAllBooksWithNoLoans() {
        return ResponseEntity.ok(bookService.getAllBooksWithNoLoans());
    }

    @GetMapping("/loaned/never/between")
    public ResponseEntity<List<BookDto>> getAllBooksWithNoLoansBetweenDates(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ResponseEntity.ok(bookService.getAllBooksWithNoLoansBetween(startDate, endDate));
    }

    @GetMapping("/loaned/to/{idClient}")
    public ResponseEntity<List<BookDto>> getBookLoanedToClientWithId(
            @PathVariable Integer idClient
    ) {
        return ResponseEntity.ok(bookService.getBookLoanedToClientWithId(idClient));
    }

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> registerNewBook(@RequestBody BookRequestDto bookRequestDto) {
        return ResponseEntity.ok(bookService.registerNewBook(bookRequestDto));
    }
}
