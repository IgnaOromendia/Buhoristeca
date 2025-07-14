package com.itpatagonia.Buhoristeca.exceptions;

public class ClientNotFoundException extends ExceptionLog {
    public ClientNotFoundException(Integer idClient) {
        super("El cliente con id " + idClient + " no existe");
    }
}
