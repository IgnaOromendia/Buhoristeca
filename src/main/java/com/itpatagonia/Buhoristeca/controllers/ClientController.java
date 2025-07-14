package com.itpatagonia.Buhoristeca.controllers;

import com.itpatagonia.Buhoristeca.dto.ClientDto;
import com.itpatagonia.Buhoristeca.dto.ClientRequestDto;
import com.itpatagonia.Buhoristeca.services.ClientService;

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

    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClientDto> registerNewClient(@RequestBody ClientRequestDto clientRequestDto) {
        return ResponseEntity.ok(clientService.registerNewClient(clientRequestDto));
    }

    @GetMapping("/active")
    public ResponseEntity<List<ClientDto>> getActiveClients() {
        return ResponseEntity.ok(clientService.getActiveClients());
    }

    @PutMapping("/remove/{idClient}")
    public ResponseEntity<ClientDto> removeClientWithId(@PathVariable Integer idClient) {
        return ResponseEntity.ok(clientService.removeClientWithId(idClient));
    }

    @PutMapping("/activate/{idClient}")
    public ResponseEntity<ClientDto> activateClientWithId(@PathVariable Integer idClient) {
        return ResponseEntity.ok(clientService.activateClientWithId(idClient));
    }


}
