package com.itpatagonia.Buhoristeca.exceptions;

public class BookIsNotActiveException extends ExceptionLog {

    public BookIsNotActiveException(Integer idBook) {
        super("El libro con id " + idBook + " no está activo");
    }
}
