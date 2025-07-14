package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.dto.ClientDto;
import com.itpatagonia.Buhoristeca.dto.ClientRequestDto;
import com.itpatagonia.Buhoristeca.entities.Client;
import com.itpatagonia.Buhoristeca.entities.Role;
import com.itpatagonia.Buhoristeca.exceptions.ClientAlreadyRegisteredException;
import com.itpatagonia.Buhoristeca.exceptions.ClientNotFoundException;
import com.itpatagonia.Buhoristeca.repositories.ClientRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jdk.jfr.TransitionTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleService roleService;

    @PersistenceContext
    private EntityManager entityManager;

    private final Integer activeStatus   = 1;
    private final Integer inactiveStatus = 0;

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

    public List<ClientDto> getActiveClients() {
        List<Client> clients = clientRepository.findActive();
        return clients.stream().map(Client::converToClientDto).toList();
    }

    @Transactional
    public ClientDto removeClientWithId(Integer idClient) {
        return updateClientStatus(idClient, inactiveStatus);
    }

    @Transactional
    public ClientDto activateClientWithId(Integer idClient) {
        return updateClientStatus(idClient, activeStatus);
    }

    @Transactional
    private ClientDto updateClientStatus(Integer idClient, Integer newStatus) {
        assertClientIsRegistered(idClient);

        clientRepository.updateStatus(idClient, newStatus);

        Client savedClient = clientRepository.findById(idClient).orElseThrow(() -> new ClientNotFoundException(idClient));

        // Igual que en book copy
        entityManager.refresh(savedClient);

        return savedClient.converToClientDto();
    }
}
