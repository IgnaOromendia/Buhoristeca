package com.itpatagonia.Buhoristeca.controllers;

import com.itpatagonia.Buhoristeca.dto.BookCopyDto;
import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.exceptions.BookNotFoundException;
import com.itpatagonia.Buhoristeca.exceptions.RoleNotFoundException;
import com.itpatagonia.Buhoristeca.services.BookCopyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/bookCopies")
public class BookCopyController {

    @Autowired
    private BookCopyService bookCopyService;

    @Tag(name = "Book Copies")
    @Operation(summary = "Register a new book copy")
    @ApiResponse(
            description = "Book copy registered",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookCopyDto.class)
                    )
            }
    )
    @PostMapping("/new")
    public ResponseEntity<BookCopyDto> registerNewBookCopy(
            @RequestParam Integer idBook
    ) {
        return ResponseEntity.ok(bookCopyService.registerNewBookCopies(idBook));
    }

    @Tag(name = "Book Copies")
    @Operation(summary = "Update book copy state")
    @ApiResponse(
            description = "Book copy state updated",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookCopyDto.class)
                    )
            }
    )
    @ApiResponse(
            description = "Not Found",
            responseCode = "404",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BookNotFoundException.class)
                    )
            }
    )
    @PutMapping("/update")
    public ResponseEntity<BookCopyDto> updateBookCopyState(
            @RequestParam Integer idBook,
            @RequestParam Integer idBookCopy,
            @RequestParam Integer idState
    ) {
        return ResponseEntity.ok(bookCopyService.updateStateOf(idBook, idBookCopy, idState));
    }
}
