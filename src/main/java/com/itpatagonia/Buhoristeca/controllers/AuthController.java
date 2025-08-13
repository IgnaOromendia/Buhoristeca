package com.itpatagonia.Buhoristeca.controllers;

import com.itpatagonia.Buhoristeca.dto.AuthRequestDto;
import com.itpatagonia.Buhoristeca.dto.AuthResponseDto;
import com.itpatagonia.Buhoristeca.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @Tag(name = "Authentication")
    @Operation(summary = "Backoffice user authentication")
    @ApiResponse(
            description = "Authentication success",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponseDto.class)
                    )
            }
    )
    @ApiResponse(
            responseCode = "404",
            description = "User not found",
            content = @Content
    )
    @ApiResponse(
            responseCode = "403",
            description = "Access denied"
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto request) {
        return ResponseEntity.ok(authService.auth(request));
    }
}
