package com.itpatagonia.Buhoristeca;

import com.itpatagonia.Buhoristeca.dto.LoanDto;
import com.itpatagonia.Buhoristeca.entities.BookCopy;
import com.itpatagonia.Buhoristeca.entities.Client;
import com.itpatagonia.Buhoristeca.entities.Loan;
import com.itpatagonia.Buhoristeca.exceptions.ClientAlreadyHasALoanException;
import com.itpatagonia.Buhoristeca.exceptions.ClientDoesNotHaveThisBookOnLoanException;
import com.itpatagonia.Buhoristeca.exceptions.EndDateIsAfterStartDateException;
import com.itpatagonia.Buhoristeca.repositories.LoanRepository;
import com.itpatagonia.Buhoristeca.services.BookCopyService;
import com.itpatagonia.Buhoristeca.services.BookService;
import com.itpatagonia.Buhoristeca.services.ClientService;
import com.itpatagonia.Buhoristeca.services.LoanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private BookService bookService;

    @Mock
    private ClientService clientService;

    @Mock
    private BookCopyService bookCopyService;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService loanService;

    private final int clientId = 1;
    private final int bookId = 1;

    @Test
    void test01AClientCanLoanIfItHasNoLoan() {
        Client client = mock(Client.class);
        BookCopy bookCopy = mock(BookCopy.class);
        Loan loan = mock(Loan.class);
        LoanDto loanDto = mock(LoanDto.class);

        doNothing().when(bookService).assertBookExists(anyInt());
        doNothing().when(bookService).assertBookIsActive(anyInt());

        when(clientService.getClientById(anyInt())).thenReturn(client);
        doNothing().when(clientService).assertClientIsActive(anyInt());

        when(loanRepository.findActiveLoanToClientWithId(clientId)).thenReturn(null);

        when(bookCopyService.getAvailableCopy(anyInt())).thenReturn(bookCopy);

        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        when(loan.convertToDto()).thenReturn(loanDto);

        LoanDto savedLoanDto = loanService.registerNewLoan(clientId, bookId, LocalDate.now().plusDays(10));

        assertNotNull(savedLoanDto);
        assertEquals(loanDto, savedLoanDto);
    }

    @Test
    void test02AClientCantLoanIfItHasLoan() {
        Client client = mock(Client.class);
        Loan loan = mock(Loan.class);

        doNothing().when(bookService).assertBookExists(anyInt());
        doNothing().when(bookService).assertBookIsActive(anyInt());

        when(clientService.getClientById(anyInt())).thenReturn(client);
        doNothing().when(clientService).assertClientIsActive(anyInt());

        when(loanRepository.findActiveLoanToClientWithId(clientId)).thenReturn(loan);

        assertThrows(ClientAlreadyHasALoanException.class, () -> loanService.registerNewLoan(clientId, bookId, LocalDate.now().plusDays(10)));
    }

    @Test
    void test03AClientCantLoanWithAnInvalidReturnDate() {
        assertThrows(EndDateIsAfterStartDateException.class, () -> loanService.registerNewLoan(clientId, bookId, LocalDate.now().minusDays(10)));
    }

    @Test
    void test04AClientCanReturnABookIfItHasTheCopy() {
        Loan loan = mock(Loan.class);
        LoanDto loanDto = mock(LoanDto.class);
        int bookCopyId = 1;
        int loanId = 1;

        doNothing().when(clientService).assertClientIsRegistered(clientId);
        doNothing().when(bookService).assertBookExists(bookId);

        when(loanRepository.findIdLoanBy(clientId, bookId, bookCopyId)).thenReturn(loanId);

        doNothing().when(loanRepository).updateReturnDateOfLoanWithId(loanId);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(loan.convertToDto()).thenReturn(loanDto);

        doNothing().when(bookCopyService).updateStateToAvailableOf(bookId, bookCopyId);

        LoanDto savedLoanDto = loanService.registerLoanReturn(clientId, bookId, bookCopyId);

        assertNotNull(savedLoanDto);
        assertEquals(loanDto, savedLoanDto);
    }

    @Test
    void test05AClientCantReturnABookIfItHasNotTheCopy() {
        int bookCopyId = 1;

        doNothing().when(clientService).assertClientIsRegistered(clientId);
        doNothing().when(bookService).assertBookExists(bookId);

        when(loanRepository.findIdLoanBy(clientId, bookId, bookCopyId)).thenReturn(null);

        assertThrows(ClientDoesNotHaveThisBookOnLoanException.class, () -> loanService.registerLoanReturn(clientId, bookId, 1));
    }

    @Test
    void test06AClientCanLoanWithOutSpecifyingReturnDateIfItHasNoLoan() {
        Client client = mock(Client.class);
        BookCopy bookCopy = mock(BookCopy.class);
        Loan loan = mock(Loan.class);
        LoanDto loanDto = mock(LoanDto.class);

        doNothing().when(bookService).assertBookExists(anyInt());
        doNothing().when(bookService).assertBookIsActive(anyInt());

        when(clientService.getClientById(anyInt())).thenReturn(client);
        doNothing().when(clientService).assertClientIsActive(anyInt());

        when(loanRepository.findActiveLoanToClientWithId(clientId)).thenReturn(null);

        when(bookCopyService.getAvailableCopy(anyInt())).thenReturn(bookCopy);

        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        when(loan.convertToDto()).thenReturn(loanDto);

        LoanDto savedLoanDto = loanService.registerNewLoan(clientId, bookId, null);

        assertNotNull(savedLoanDto);
        assertEquals(loanDto, savedLoanDto);
    }

}
