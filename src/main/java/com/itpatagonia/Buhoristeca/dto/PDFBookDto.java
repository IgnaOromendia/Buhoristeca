package com.itpatagonia.Buhoristeca.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PDFBookDto {

    @JsonProperty
    private final String title;

    @JsonProperty
    private final String author;

    private final byte[] file;

    public PDFBookDto(String title, String author, byte[] file) {
        this.title = title;
        this.author = author;
        this.file = file;
    }
}
