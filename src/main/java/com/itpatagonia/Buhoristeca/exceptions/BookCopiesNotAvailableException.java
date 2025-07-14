package com.itpatagonia.Buhoristeca.exceptions;

public class BookCopiesNotAvailableException extends ExceptionLog{

    public BookCopiesNotAvailableException(Integer idBook) {
        super("No hya copias disponibles del libro con id " + idBook);
    }
}
