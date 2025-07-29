package com.itpatagonia.Buhoristeca.controllers;

import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.dto.ClientDto;
import com.itpatagonia.Buhoristeca.dto.PDFBookDto;
import com.itpatagonia.Buhoristeca.entities.Book;
import com.itpatagonia.Buhoristeca.exceptions.BookNotFoundException;
import com.itpatagonia.Buhoristeca.exceptions.ClientNotFoundException;
import com.itpatagonia.Buhoristeca.services.PDFBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user/pdfBook")
public class PDFBookController {

    @Autowired
    private PDFBookService pdfBookService;

    @Tag(name = "PDF Book")
    @Operation(summary = "Upload a pdf book")
    @ApiResponse(
            description = "PDF uploaded",
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
    @PostMapping("/upload")
    public ResponseEntity<BookDto> uploadPDFBook(
            @RequestParam Integer idBook,
            @RequestParam MultipartFile file
            ) {
        return ResponseEntity.ok(pdfBookService.uploadPDFBook(idBook, file));
    }

    @Tag(name = "PDF Book")
    @Operation(summary = "Download a pdf book")
    @ApiResponse(
            description = "PDF downloaded",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = byte.class)
                    )
            }
    )
    @ApiResponse(
            description = "Not Found",
            responseCode = "404",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientNotFoundException.class)
                    )
            }
    )
    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadPDFBook (
            @RequestParam Integer idBook,
            @RequestParam Integer idPdfBook
    ) {
        return ResponseEntity.ok(pdfBookService.downloadPDFBookWithId(idBook, idPdfBook));
    }

}
