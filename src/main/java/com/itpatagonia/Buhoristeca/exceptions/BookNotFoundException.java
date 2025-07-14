package com.itpatagonia.Buhoristeca.exceptions;

public class BookNotFoundException extends ExceptionLog {

    public BookNotFoundException(Integer idBook) {
        super("El libro con id " + idBook + " no existe");
    }

}
