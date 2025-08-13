package com.itpatagonia.Buhoristeca.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication Response DTO")
public class AuthResponseDto {

    @Schema(
            name = "username",
            title = "User username or email",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String username;

    public AuthResponseDto(String username) {
        this.username = username;
    }
}
