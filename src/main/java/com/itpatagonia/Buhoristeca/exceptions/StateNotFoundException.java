package com.itpatagonia.Buhoristeca.exceptions;

public class StateNotFoundException extends ExceptionLog {
    public StateNotFoundException(Integer idState) {
        super("El estado con id " + idState + " no existe");
    }
}
