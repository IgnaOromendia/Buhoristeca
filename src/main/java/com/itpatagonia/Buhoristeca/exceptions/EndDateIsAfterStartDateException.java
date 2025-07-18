package com.itpatagonia.Buhoristeca.exceptions;

public class EndDateIsAfterStartDateException extends ExceptionLog {
    public EndDateIsAfterStartDateException() {
        super("Período de tiempo inválido");
    }
}
