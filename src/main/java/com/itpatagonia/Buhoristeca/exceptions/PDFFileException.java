package com.itpatagonia.Buhoristeca.exceptions;

public class PDFFileException extends ExceptionLog {
    public PDFFileException(Integer idBook, String message) {
        super("Subiendo pdf del libro con id " + idBook + ": " + message);
    }
}
