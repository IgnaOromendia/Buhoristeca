package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.dto.ClientDto;
import com.itpatagonia.Buhoristeca.dto.ClientRequestDto;
import com.itpatagonia.Buhoristeca.entities.Client;
import com.itpatagonia.Buhoristeca.entities.Role;
import com.itpatagonia.Buhoristeca.exceptions.ClientAlreadyRegisteredException;
import com.itpatagonia.Buhoristeca.exceptions.ClientNotFoundException;
import com.itpatagonia.Buhoristeca.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleService roleService;

    public ClientDto registerNewClient(ClientRequestDto clientRequestDto) {
        assertClientIsNotRegistered(clientRequestDto.getClientId());

        Role role = roleService.getRoleById(clientRequestDto.getIdRole());

        Client savedClient = clientRepository.save(clientRequestDto.convertToClient(role));

        return savedClient.converToClientDto();
    }

    public Client getClientById(Integer idClient) {
        return assertClientIsRegistered(idClient);
    }

    // Asserts

    private void assertClientIsNotRegistered(Integer idClient) {
        if (clientRepository.findById(idClient).isPresent())
            throw new ClientAlreadyRegisteredException(idClient);
    }

    public Client assertClientIsRegistered(Integer idClient) {
        return clientRepository.findById(idClient).orElseThrow(() -> new ClientNotFoundException(idClient));
    }

}
