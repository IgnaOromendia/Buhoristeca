package com.itpatagonia.Buhoristeca.exceptions;

public class PDFBookNotFoundException extends ExceptionLog {
    public PDFBookNotFoundException(Integer idBook, Integer idPDFBook) {
        super("El pdf con id " + idPDFBook + " del libro con id " + idBook + " no existe");
    }
}
