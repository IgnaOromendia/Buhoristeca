package com.itpatagonia.Buhoristeca.exceptions;

public class LanguageNotFoundException extends ExceptionLog {
    public LanguageNotFoundException(Integer idLanguage) {
        super("El idioma con id " + idLanguage + " no existe");
    }
}
