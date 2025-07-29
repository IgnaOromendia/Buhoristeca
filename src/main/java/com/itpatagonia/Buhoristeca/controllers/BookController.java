package com.itpatagonia.Buhoristeca.controllers;

import com.itpatagonia.Buhoristeca.dto.BookCopiesAmountDto;
import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.dto.BookRequestDto;
import com.itpatagonia.Buhoristeca.exceptions.BookNotFoundException;
import com.itpatagonia.Buhoristeca.exceptions.ClientNotFoundException;
import com.itpatagonia.Buhoristeca.exceptions.EndDateIsAfterStartDateException;
import com.itpatagonia.Buhoristeca.exceptions.RoleNotFoundException;
import com.itpatagonia.Buhoristeca.services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "Books")
    @Operation(summary = "Returns a list of loaned books")
    @ApiResponse(
            description = "Book list",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )
            }
    )
    @ApiResponse(
            description = "Role Not Found",
            responseCode = "404",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RoleNotFoundException.class)
                    )
            }
    )
    @GetMapping("/loaned/role/{idRole}")
    public ResponseEntity<List<BookDto>> getLoanedBook(
            @PathVariable Integer idRole,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        return ResponseEntity.ok(bookService.getAllLoanedBooksByRole(idRole, startDate, endDate));
    }

    @Tag(name = "Books")
    @Operation(summary = "Returns a list of the amount of book copies")
    @ApiResponse(
            description = "Books copies amount",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookCopiesAmountDto.class)
                    )
            }
    )
    @GetMapping("/copies")
    public ResponseEntity<List<BookCopiesAmountDto>> getAmountOfCopies() {
        return ResponseEntity.ok(bookService.getAllCopiesAmount());
    }

    @Tag(name = "Books")
    @Operation(summary = "Returns a list of the amount of book copies from a particular book")
    @ApiResponse(
            description = "Book copies amount",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookCopiesAmountDto.class)
                    )
            }
    )
    @ApiResponse(
            description = "Book Not Found",
            responseCode = "404",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookNotFoundException.class)
                    )
            }
    )
    @GetMapping("/copies/{idBook}")
    public ResponseEntity<BookCopiesAmountDto> getAmountOfCopiesByBook(
            @PathVariable Integer idBook
    ) {
        return ResponseEntity.ok(bookService.getAllCopiesAmountByBook(idBook));
    }

    @Tag(name = "Books")
    @Operation(summary = "Returns a list of books that never were loaned")
    @ApiResponse(
            description = "Book list",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )
            }
    )
    @GetMapping("/loaned/never")
    public ResponseEntity<List<BookDto>> getAllBooksWithNoLoans() {
        return ResponseEntity.ok(bookService.getAllBooksWithNoLoans());
    }

    @Tag(name = "Books")
    @Operation(summary = "Returns a list of books that never were loaned between two dates")
    @ApiResponse(
            description = "Book list",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )
            }
    )
    @ApiResponse(
            description = "Date exception",
            responseCode = "403",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = EndDateIsAfterStartDateException.class)
                    )
            }
    )
    @GetMapping("/loaned/never/between")
    public ResponseEntity<List<BookDto>> getAllBooksWithNoLoansBetweenDates(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        return ResponseEntity.ok(bookService.getAllBooksWithNoLoansBetween(startDate, endDate));
    }

    @Tag(name = "Books")
    @Operation(summary = "Returns a list of books loaned to a particular client")
    @ApiResponse(
            description = "Client´s loaned books",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )
            }
    )
    @ApiResponse(
            description = "Client Not Found",
            responseCode = "404",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientNotFoundException.class)
                    )
            }
    )
    @GetMapping("/loaned/to/{idClient}")
    public ResponseEntity<List<BookDto>> getBookLoanedToClientWithId(
            @PathVariable Integer idClient
    ) {
        return ResponseEntity.ok(bookService.getBookLoanedToClientWithId(idClient));
    }

    @Tag(name = "Books")
    @Operation(summary = "Register a new book")
    @ApiResponse(
            description = "Book created",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )
            }
    )
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDto> registerNewBook(@RequestBody BookRequestDto bookRequestDto) {
        return ResponseEntity.ok(bookService.registerNewBook(bookRequestDto));
    }

    @Tag(name = "Books")
    @Operation(summary = "Remove a book")
    @ApiResponse(
            description = "Book removed",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )
            }
    )
    @ApiResponse(
            description = "Book Not Found",
            responseCode = "404",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookNotFoundException.class)
                    )
            }
    )
    @PutMapping("/remove/{idBook}")
    public ResponseEntity<BookDto> removeBookWithId(@PathVariable Integer idBook) {
        return ResponseEntity.ok(bookService.removeBookWithId(idBook));
    }

    @Tag(name = "Books")
    @Operation(summary = "Activate a book")
    @ApiResponse(
            description = "Book activated",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )
            }
    )
    @ApiResponse(
            description = "Book Not Found",
            responseCode = "404",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookNotFoundException.class)
                    )
            }
    )
    @PutMapping("/activate/{idBook}")
    public ResponseEntity<BookDto> activateBookWithId(@PathVariable Integer idBook) {
        return ResponseEntity.ok(bookService.activateBookWithId(idBook));
    }

    @Tag(name = "Books")
    @Operation(summary = "Returns a list of current active books")
    @ApiResponse(
            description = "List of active books",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookDto.class)
                    )
            }
    )
    @GetMapping("/active")
    public ResponseEntity<List<BookDto>> getActiveBooks() {
        return ResponseEntity.ok(bookService.getActiveBooks());
    }
}
