package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.dto.LoanDto;
import com.itpatagonia.Buhoristeca.entities.BookCopy;
import com.itpatagonia.Buhoristeca.entities.Client;
import com.itpatagonia.Buhoristeca.entities.Loan;
import com.itpatagonia.Buhoristeca.exceptions.ClientAlreadyHasALoanException;
import com.itpatagonia.Buhoristeca.exceptions.ClientDoesNotHaveThisBookOnLoanException;
import com.itpatagonia.Buhoristeca.exceptions.LoanNotFoundException;
import com.itpatagonia.Buhoristeca.repositories.LoanRepository;

import com.itpatagonia.Buhoristeca.util.DateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private BookService bookService;

    @Autowired
    private BookCopyService bookCopyService;

    public LoanDto registerNewLoan(Integer idClient, Integer idBook, LocalDate returnDate) {
        if (returnDate != null) DateValidator.assertEndDateIsAfterStartDate(LocalDate.now(), returnDate);

        bookService.assertBookExists(idBook);
        bookService.assertBookIsActive(idBook);

        Client client = clientService.getClientById(idClient);
        clientService.assertClientIsActive(idClient);

        assertClientDoesNotHaveAnotherActiveLoan(idClient);

        BookCopy bookCopy   = bookCopyService.getAvailableCopy(idBook);

        Loan savedLoan = loanRepository.save(new Loan(bookCopy, client, returnDate));

        bookCopy.updateStateToNotAvailableOn(bookCopyService);

        return savedLoan.convertToDto();
    }

    public LoanDto registerLoanReturn(Integer idClient, Integer idBook, Integer idBookCopy) {
        clientService.assertClientIsRegistered(idClient);
        bookService.assertBookExists(idBook);

        Integer idLoan = assertBookHasBeenLoanedToClient(idClient, idBook, idBookCopy);

        loanRepository.updateReturnDateOfLoanWithId(idLoan);
        Loan savedLoan = loanRepository.findById(idLoan).orElseThrow(() -> new LoanNotFoundException(idBook, idBookCopy));

        bookCopyService.updateStateToAvailableOf(idBook, idBookCopy);

        return savedLoan.convertToDto();
    }

    @Scheduled(fixedRate = 86400000) // 1 día son 86.400.000 milisegundos
    private void updateExpiredLoanStatus() {
        loanRepository.updateExpiredLoanStatus();
    }

    // Asserts

    private void assertClientDoesNotHaveAnotherActiveLoan(Integer idClient) {
        if (loanRepository.findActiveLoanToClientWithId(idClient) != null)
            throw new ClientAlreadyHasALoanException(idClient);
    }

    private Integer assertBookHasBeenLoanedToClient(Integer idClient, Integer idBook, Integer idBookCopy) {
        Integer loan = loanRepository.findIdLoanBy(idClient, idBook, idBookCopy);
        if (loan == null)
            throw new ClientDoesNotHaveThisBookOnLoanException(idClient);
        return loan;
    }


}
