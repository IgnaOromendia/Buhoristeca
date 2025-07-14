package com.itpatagonia.Buhoristeca.exceptions;

public class LoanNotFoundException extends RuntimeException {
    public LoanNotFoundException(Integer idBook, Integer idBookCopy) {
        super("Error al buscar el prestamos del libro con id " + idBook + " con copia de " + idBookCopy);
    }
}
