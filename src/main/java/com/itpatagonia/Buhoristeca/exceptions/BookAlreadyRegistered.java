package com.itpatagonia.Buhoristeca.exceptions;

public class BookAlreadyRegistered extends RuntimeException {

    public BookAlreadyRegistered(String title) {
        super("El libro de titulo: " + title + ", ya existe");
    }
}
