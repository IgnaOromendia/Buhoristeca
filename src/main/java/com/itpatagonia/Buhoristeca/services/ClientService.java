package com.itpatagonia.Buhoristeca.services;

import com.itpatagonia.Buhoristeca.dto.ClientDto;
import com.itpatagonia.Buhoristeca.dto.ClientRequestDto;
import com.itpatagonia.Buhoristeca.entities.Client;
import com.itpatagonia.Buhoristeca.entities.Role;
import com.itpatagonia.Buhoristeca.repositories.ClientRepository;
import com.itpatagonia.Buhoristeca.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private RoleRepository roleRepository;

    public ClientDto registerNewClient(ClientRequestDto clientRequestDto) {
        assertClientIsNotRegistered(clientRequestDto.getClientId());

        Role role = assertRoleExists(clientRequestDto.getIdRole());

        Client savedClient = clientRepository.save(clientRequestDto.convertToClient(role));

        return savedClient.converToClientDto();
    }

    // Asserts

    private void assertClientIsNotRegistered(Integer idClient) {
        if (clientRepository.findById(idClient).isPresent()) throw new RuntimeException("El cliente con id " + idClient + " ya está registrado");
    }

    private Role assertRoleExists(Integer idRole) {
        Optional<Role> role = roleRepository.findById(idRole);

        if (role.isEmpty()) throw new RuntimeException("El rol con id " + idRole + " no existe");

        return role.get();
    }

}
