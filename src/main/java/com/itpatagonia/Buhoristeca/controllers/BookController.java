package com.itpatagonia.Buhoristeca.controllers;

import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.services.BookService;

import org.springframework.beans.factory.annotation.Autowired;
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
        return ResponseEntity.ok(bookService.getAllLoanedBooks(idRole, startDate, endDate));
    }
}
