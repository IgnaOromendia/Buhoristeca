package com.itpatagonia.Buhoristeca.exceptions;

public class ClientIsNotActiveException extends ExceptionLog {
    public ClientIsNotActiveException(Integer idClient) {
        super("El cliente con id " + idClient + " no está activo");
    }
}
