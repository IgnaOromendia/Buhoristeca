package com.itpatagonia.Buhoristeca.controllers;

import com.itpatagonia.Buhoristeca.dto.LoanDto;
import com.itpatagonia.Buhoristeca.services.LoanService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/user/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/new")
    public ResponseEntity<LoanDto> registerNewLoan(
            @RequestParam Integer idClient,
            @RequestParam Integer idBook,
            @RequestParam(required = false) LocalDate returnDate) {
        return ResponseEntity.ok(loanService.registerNewLoan(idClient, idBook, returnDate));
    }

    @PutMapping("/return")
    public ResponseEntity<LoanDto> registerLoanReturn(
            @RequestParam Integer idClient,
            @RequestParam Integer idBook,
            @RequestParam Integer idBookCopy) {
        return ResponseEntity.ok(loanService.registerLoanReturn(idClient, idBook, idBookCopy));
    }

}
