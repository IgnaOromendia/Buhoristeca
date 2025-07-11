package com.itpatagonia.Buhoristeca.controllers;

import com.itpatagonia.Buhoristeca.dto.BookCopyDto;
import com.itpatagonia.Buhoristeca.services.BookCopyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
