package com.itpatagonia.Buhoristeca.exceptions;

import java.util.Set;

public class GenresNotFoundException extends ExceptionLog {
    public GenresNotFoundException(Set<Integer> genres) {
        super("Los géneros con id " + genres + " no existen");
    }
}
