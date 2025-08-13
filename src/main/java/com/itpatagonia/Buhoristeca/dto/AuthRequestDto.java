package com.itpatagonia.Buhoristeca.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication Request DTO")
public class AuthRequestDto {

    @Schema(
            name = "username",
            title = "User username or email",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    @Schema(
            name = "password",
            title = "User password",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String password;

    public String getUsername() { return username; }

    public String getPassword() { return password; }
}
