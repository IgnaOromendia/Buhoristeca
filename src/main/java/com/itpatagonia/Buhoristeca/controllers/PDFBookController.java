package com.itpatagonia.Buhoristeca.controllers;

import com.itpatagonia.Buhoristeca.dto.BookDto;
import com.itpatagonia.Buhoristeca.dto.PDFBookDto;
import com.itpatagonia.Buhoristeca.services.PDFBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user/pdfBook")
public class PDFBookController {

    @Autowired
    private PDFBookService pdfBookService;

    @PostMapping("/upload")
    public ResponseEntity<BookDto> uploadPDFBook(
            @RequestParam Integer idBook,
            @RequestParam MultipartFile file
            ) {
        return ResponseEntity.ok(pdfBookService.uploadPDFBook(idBook, file));
    }

    @GetMapping("/download")
    public ResponseEntity<PDFBookDto> downloadPDFBook (
            @RequestParam Integer idBook,
            @RequestParam Integer idPdfBook
    ) {
        return ResponseEntity.ok(pdfBookService.downloadPDFBookWithId(idBook, idPdfBook));
    }

}
