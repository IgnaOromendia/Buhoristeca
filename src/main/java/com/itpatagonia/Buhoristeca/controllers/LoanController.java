package com.itpatagonia.Buhoristeca.controllers;

import com.itpatagonia.Buhoristeca.dto.ClientDto;
import com.itpatagonia.Buhoristeca.dto.LoanDto;
import com.itpatagonia.Buhoristeca.exceptions.ClientNotFoundException;
import com.itpatagonia.Buhoristeca.services.LoanService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/user/loan")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Tag(name = "Loan")
    @Operation(summary = "Register a new loan")
    @ApiResponse(
            description = "Loan registered",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = LoanDto.class)
                    )
            }
    )
    @PostMapping("/new")
    public ResponseEntity<LoanDto> registerNewLoan(
            @RequestParam Integer idClient,
            @RequestParam Integer idBook,
            @RequestParam(required = false) LocalDate returnDate) {
        return ResponseEntity.ok(loanService.registerNewLoan(idClient, idBook, returnDate));
    }

    @Tag(name = "Loan")
    @Operation(summary = "Returns client´s loan of a book copy")
    @ApiResponse(
            description = "Client´s loan",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class)
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
    @PutMapping("/return")
    public ResponseEntity<LoanDto> registerLoanReturn(
            @RequestParam Integer idClient,
            @RequestParam Integer idBook,
            @RequestParam Integer idBookCopy) {
        return ResponseEntity.ok(loanService.registerLoanReturn(idClient, idBook, idBookCopy));
    }

}
