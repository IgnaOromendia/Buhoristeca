package com.itpatagonia.Buhoristeca.exceptions;

public class AuthorNotFoundException extends ExceptionLog {

    public AuthorNotFoundException(Integer idAuthor) {
        super("El autor con id " + idAuthor + " no existe");
    }
}
