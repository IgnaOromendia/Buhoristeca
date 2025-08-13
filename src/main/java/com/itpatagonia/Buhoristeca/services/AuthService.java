package com.itpatagonia.Buhoristeca.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itpatagonia.Buhoristeca.dto.AuthRequestDto;
import com.itpatagonia.Buhoristeca.dto.AuthResponseDto;
import com.itpatagonia.Buhoristeca.exceptions.BothCredentialsMandatoryException;

import com.itpatagonia.Buhoristeca.exceptions.ExceptionLog;
import com.itpatagonia.Buhoristeca.exceptions.LoginAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;

@Service
public class AuthService {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${apibuhoristeca.auth.url}")
    private String authUrl;

    @Value("${password.hash.algorithm}")
    private String hashAlgorithm;

    public AuthResponseDto auth(AuthRequestDto request) {
        String username = request.getUsername();
        String password = request.getPassword();

        assertUsernameAndPasswordAreNotEmpty(username, password);

        String hashedPassword = hashPassword(password);

        String token = this.jwtTokenService.generateToken();

        return validateUserCredentials(username, hashedPassword, token);
    }


    private AuthResponseDto validateUserCredentials(String username, String hashedPassword, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        String requestBody = String.format("{\"clave\": \"%s\", \"usuario\": \"%s\"}", hashedPassword, username);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = this.restTemplate.exchange(this.authUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) return null;

        String responseBody = response.getBody();

        JsonNode jsonNode = parseJson(responseBody);

        assertLoginCredentialsAreNotEmpty(jsonNode);

        JsonNode data = jsonNode.get("datos");

        if (!data.has("username")) throw new LoginAuthenticationException();

        return new AuthResponseDto(data.get("username").asText());
    }

    private String hashPassword(String originalPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance(hashAlgorithm);
            byte[] bytes = md.digest(originalPassword.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(bytes);
        } catch (Exception e) {
            throw new ExceptionLog("Error al encrpitar la contraseña.\n" + e.getMessage());
        }
    }

    private JsonNode parseJson(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(responseBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error procesando el JSON de la respuesta.", e);
        }
    }

    private void assertUsernameAndPasswordAreNotEmpty(String username, String password) {
        if (username == null || password == null) throw new BothCredentialsMandatoryException();
    }

    private void assertLoginCredentialsAreNotEmpty(JsonNode jsonNode) {
        if (jsonNode == null) throw new LoginAuthenticationException();
    }
}
