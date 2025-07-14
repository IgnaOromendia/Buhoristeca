package com.itpatagonia.Buhoristeca.exceptions;

public class BookCopyNotFoundException extends ExceptionLog {

    public BookCopyNotFoundException(Integer idBookCopy, Integer idBook) {
        super("La copia con id " + idBookCopy + " del libro con id " + idBook + " no existe");
    }
}
