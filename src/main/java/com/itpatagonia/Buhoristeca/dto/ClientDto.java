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

    @JsonProperty
    private final Integer status;

    public ClientDto(String name, String lastName, Role role, Integer status) {
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.status = status;
    }

}
