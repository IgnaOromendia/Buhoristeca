package com.itpatagonia.Buhoristeca.exceptions;

public class ClientAlreadyHasALoanException extends ExceptionLog {
    public ClientAlreadyHasALoanException(Integer idClient) {
        super("El cliente con id " + idClient + " ya tiene un préstamo");
    }
}
