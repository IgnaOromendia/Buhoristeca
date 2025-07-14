package com.itpatagonia.Buhoristeca.exceptions;

public class BookAlreadyRegisteredException extends ExceptionLog {

    public BookAlreadyRegisteredException(String title) {
        super("El libro de titulo: " + title + ", ya existe");
    }
}
