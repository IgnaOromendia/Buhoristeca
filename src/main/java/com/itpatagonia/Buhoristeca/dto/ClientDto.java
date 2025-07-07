package com.itpatagonia.Buhoristeca.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.itpatagonia.Buhoristeca.entities.Role;

public class ClientDto {

    @JsonProperty
    private final String name;

    @JsonProperty
    private final String lastName;

    @JsonProperty
    private final Role role;

    public ClientDto(String name, String lastName, Role role) {
        this.name = name;
        this.lastName = lastName;
        this.role = role;
    }

}
