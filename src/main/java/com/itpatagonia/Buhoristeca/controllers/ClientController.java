package com.itpatagonia.Buhoristeca.controllers;

import com.itpatagonia.Buhoristeca.dto.BookCopyDto;
import com.itpatagonia.Buhoristeca.dto.ClientDto;
import com.itpatagonia.Buhoristeca.dto.ClientRequestDto;
import com.itpatagonia.Buhoristeca.entities.Client;
import com.itpatagonia.Buhoristeca.exceptions.ClientNotFoundException;
import com.itpatagonia.Buhoristeca.services.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/user/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Tag(name = "Client")
    @Operation(summary = "Register a new client")
    @ApiResponse(
            description = "Client registered",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class)
                    )
            }
    )
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientDto> registerNewClient(@RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.ok(clientService.registerNewClient(clientRequestDto));
    }

    @Tag(name = "Client")
    @Operation(summary = "List of active clients")
    @ApiResponse(
            description = "List of active clients",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class)
                    )
            }
    )
    @GetMapping("/active")
    public ResponseEntity<List<ClientDto>> getActiveClients() {
        return ResponseEntity.ok(clientService.getActiveClients());
    }

    @Tag(name = "Client")
    @Operation(summary = "Remove a client")
    @ApiResponse(
            description = "Client removed",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class)
                    )
            }
    )
    @ApiResponse(
            description = "Client Not Found",
            responseCode = "404",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientNotFoundException.class)
                    )
            }
    )
    @PutMapping("/remove/{idClient}")
    public ResponseEntity<ClientDto> removeClientWithId(@PathVariable Integer idClient) {
        return ResponseEntity.ok(clientService.removeClientWithId(idClient));
    }

    @Tag(name = "Client")
    @Operation(summary = "Activate a client")
    @ApiResponse(
            description = "Client activated",
            responseCode = "200",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientDto.class)
                    )
            }
    )
    @ApiResponse(
            description = "Client Not Found",
            responseCode = "404",
            content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ClientNotFoundException.class)
                    )
            }
    )
    @PutMapping("/activate/{idClient}")
    public ResponseEntity<ClientDto> activateClientWithId(@PathVariable Integer idClient) {
        return ResponseEntity.ok(clientService.activateClientWithId(idClient));
    }


}
