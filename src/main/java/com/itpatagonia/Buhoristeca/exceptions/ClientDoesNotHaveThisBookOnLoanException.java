package com.itpatagonia.Buhoristeca.exceptions;

public class ClientDoesNotHaveThisBookOnLoanException extends ExceptionLog {
    public ClientDoesNotHaveThisBookOnLoanException(Integer idClient) {
        super("El cliente con id " + idClient + " no tiene un préstamo sobre esta copia del libro");
    }
}
