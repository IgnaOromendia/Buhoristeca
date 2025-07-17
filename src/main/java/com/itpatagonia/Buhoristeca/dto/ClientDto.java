package com.itpatagonia.Buhoristeca.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.itpatagonia.Buhoristeca.entities.Role;

import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ClientDto other = (ClientDto) obj;
        return Objects.equals(name, other.name) &&
                Objects.equals(lastName, other.lastName) &&
                Objects.equals(role, other.role) &&
                Objects.equals(status, other.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, lastName, role, status);
    }


}
