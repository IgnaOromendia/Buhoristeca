package com.itpatagonia.Buhoristeca.exceptions;

public class PublisherNotFoundException extends ExceptionLog {
    public PublisherNotFoundException(Integer idPublisher) {
        super("La editorial con id " + idPublisher + " no existe");
    }
}
