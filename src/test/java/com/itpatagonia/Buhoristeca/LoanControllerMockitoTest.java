package com.itpatagonia.Buhoristeca;

import com.itpatagonia.Buhoristeca.controllers.LoanController;
import com.itpatagonia.Buhoristeca.dto.LoanDto;
import com.itpatagonia.Buhoristeca.services.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoanControllerMockitoTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    private final int idClient = 1;
    private final int idBook = 1;
    private final LocalDate returnDate = LocalDate.now().plusDays(10);

    private LoanDto loanDto;

    @BeforeEach
    void setUp() {
        loanDto = mock(LoanDto.class);
    }

    @Test
    void registerNewLoan_validRequest_returnsOk() {
        when(loanService.registerNewLoan(idClient, idBook, returnDate)).thenReturn(loanDto);

        ResponseEntity<LoanDto> response = loanController.registerNewLoan(idClient, idBook, returnDate);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), loanDto);
    }

    @Test
    void registerLoanReturn_validRequest_returnsOk() {
        when(loanService.registerLoanReturn(idClient, idBook, 1)).thenReturn(loanDto);

        ResponseEntity<LoanDto> response = loanController.registerLoanReturn(idClient, idBook, 1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(response.getBody(), loanDto);
    }
}
