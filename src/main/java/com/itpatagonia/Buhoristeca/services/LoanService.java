package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.dto.LoanDto;
import com.itpatagonia.Buhoristeca.entities.BookCopy;
import com.itpatagonia.Buhoristeca.entities.Client;
import com.itpatagonia.Buhoristeca.entities.Loan;
import com.itpatagonia.Buhoristeca.repositories.BookCopyRepository;
import com.itpatagonia.Buhoristeca.repositories.BookRepository;
import com.itpatagonia.Buhoristeca.repositories.ClientRepository;
import com.itpatagonia.Buhoristeca.repositories.LoanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCopyRepository bookCopyRepository;

    @Autowired
    private BookCopyService bookCopyService;

    public LoanDto registerNewLoan(Integer idClient, Integer idBook) {
        Client client   = assertClientExists(idClient);

        assertBookExists(idBook);
        assertClientDoesNotHaveAnotherActiveLoan(idClient);

        BookCopy bookCopy = bookCopyService.getAvailableCopy(idBook);

        if (bookCopy == null)
            throw new RuntimeException("No hya copias disponibles del libro con id " + idBook);

        bookCopy.updateStateToNotAvailableOn(bookCopyService);

        Loan savedLoan = loanRepository.save(new Loan(bookCopy, client));

        return savedLoan.convertToDto();
    }


    // Asserts

    private Client assertClientExists(Integer idClient) {
        Optional<Client> client = clientRepository.findById(idClient);

        if (client.isEmpty()) throw new RuntimeException("El cliente con id " + idClient + " no existe");

        return client.get();
    }

    private void assertBookExists(Integer idBook) {
        if (bookRepository.findById(idBook).isEmpty())
            throw new RuntimeException("El libro con id " + idBook + " no existe");
    }

    private void assertClientDoesNotHaveAnotherActiveLoan(Integer idClient) {
        if (loanRepository.findActiveLoanToClientWithId(idClient) != null)
            throw new RuntimeException("El cliente con id " + idClient + " ya tiene un préstamo");
    }
}
