package com.itpatagonia.Buhoristeca.controllers;

import com.itpatagonia.Buhoristeca.dto.BookCopyDto;
import com.itpatagonia.Buhoristeca.services.BookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/bookCopies")
public class BookCopyController {

    @Autowired
    private BookCopyService bookCopyService;

    @PostMapping("/new")
    public ResponseEntity<BookCopyDto> registerNewBookCopy(
            @RequestParam Integer idBook
    ) {
        return ResponseEntity.ok(bookCopyService.registerNewBookCopies(idBook));
    }

    @PutMapping("/update")
    public ResponseEntity<BookCopyDto> updateBookCopyState(
            @RequestParam Integer idBook,
            @RequestParam Integer idBookCopy,
            @RequestParam Integer idState
    ) {
        return ResponseEntity.ok(bookCopyService.updateStateOf(idBook, idBookCopy, idState));
    }
}
