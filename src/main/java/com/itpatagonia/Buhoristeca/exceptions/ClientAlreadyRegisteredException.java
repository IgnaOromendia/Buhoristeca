package com.itpatagonia.Buhoristeca.exceptions;

public class ClientAlreadyRegisteredException extends ExceptionLog {
    public ClientAlreadyRegisteredException(Integer idClient) {
        super("El cliente con id " + idClient + " ya está registrado");
    }
}
