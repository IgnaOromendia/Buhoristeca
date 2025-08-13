package com.itpatagonia.Buhoristeca.exceptions;

public class BothCredentialsMandatoryException extends ExceptionLog {
    public BothCredentialsMandatoryException() {
        super("Username y password son obligatorios");
    }
}
