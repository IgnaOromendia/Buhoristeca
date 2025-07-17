package com.itpatagonia.Buhoristeca;

import com.itpatagonia.Buhoristeca.dto.ClientDto;
import com.itpatagonia.Buhoristeca.dto.ClientRequestDto;
import com.itpatagonia.Buhoristeca.entities.Client;
import com.itpatagonia.Buhoristeca.entities.Role;
import com.itpatagonia.Buhoristeca.exceptions.ClientAlreadyRegisteredException;
import com.itpatagonia.Buhoristeca.exceptions.ClientIsNotActiveException;
import com.itpatagonia.Buhoristeca.exceptions.ClientNotFoundException;
import com.itpatagonia.Buhoristeca.repositories.ClientRepository;
import com.itpatagonia.Buhoristeca.services.ClientService;
import com.itpatagonia.Buhoristeca.services.RoleService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private RoleService roleService;

    @InjectMocks
    private ClientService clientService;

    private final Integer clientId = 1;
    private final LocalDate birthDate = LocalDate.now().minusYears(18);
    private final ClientRequestDto clientToAdd = new ClientRequestDto(clientId, "clientName", "clientLastName", birthDate, "emial@test.com", "street 1", 1);
    private Client client;
    private Role role;

    @BeforeEach
    void setUp() {
        role = new Role(1, "role");
        client = clientToAdd.convertToClient(role);
    }

    @Test
    void test01ThereAreNoActiveClientsWhenThereAreNoClients() {
        when(clientRepository.findActive()).thenReturn(new ArrayList<>());

        List<ClientDto> activeClients = clientService.getActiveClients();
        assertTrue(activeClients.isEmpty());
    }

    @Test
    void test02OneActiveClientWhenOnlyOneClientIsAdded() {
        ClientRequestDto newClientToAdd = new ClientRequestDto(clientId + 1, "clientName", "clientLastName", birthDate, "emial@test.com", "street 1", 1);
        Client newClientAdded = newClientToAdd.convertToClient(role);

        when(roleService.getRoleById(1)).thenReturn(role);
        when(clientRepository.save(any(Client.class))).thenReturn(newClientAdded);
        when(clientRepository.findActive()).thenReturn(List.of(newClientAdded));
        when(clientRepository.findById(clientId+1)).thenReturn(Optional.of(newClientAdded));

        ClientDto savedClient = clientService.registerNewClient(newClientToAdd);
        List<ClientDto> activeClients = clientService.getActiveClients();

        assertEquals(savedClient, clientService.getClientById(clientId + 1).converToClientDto());
        assertFalse(activeClients.isEmpty());
        assertTrue(activeClients.contains(savedClient));
    }

    @Test
    void test03ClientCanBeRemoved() {
        Client clientToRemove = clientToAdd.convertToClient(role);

        when(clientRepository.existsById(clientId)).thenReturn(true);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientToRemove));

        doAnswer(inv -> {
            clientToRemove.setInactive();
            return null;
        }).when(clientRepository).updateStatus(eq(clientId), anyInt());

        assertDoesNotThrow(() -> clientService.assertClientIsActive(clientId));

        clientService.removeClientWithId(clientId);

        assertThrows(ClientIsNotActiveException.class, () -> clientService.assertClientIsActive(clientId));
    }

    @Test
    void test04ClientCanBeActivated() {
        Client clientToActivate = clientToAdd.convertToClient(role);
        clientToActivate.setInactive();

        when(clientRepository.existsById(clientId)).thenReturn(true);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(clientToActivate));

        doAnswer(inv -> {
            clientToActivate.setActive();
            return null;
        }).when(clientRepository).updateStatus(eq(clientId), anyInt());

        assertThrows(ClientIsNotActiveException.class, () -> clientService.assertClientIsActive(clientId));

        clientService.activateClientWithId(clientId);

        assertDoesNotThrow(() -> clientService.assertClientIsActive(clientId));
    }

    @Test
    void test05ClientCanNotBeRegisteredIfAlreadyExists() {
        when(clientRepository.existsById(clientId)).thenReturn(true);

        assertThrows(ClientAlreadyRegisteredException.class, () -> clientService.registerNewClient(clientToAdd));
    }

    @Test
    void test06ClientStatCanNotBeUpdatedIfItIsNotRegistered() {
        when(clientRepository.existsById(clientId)).thenReturn(false);

        assertThrows(ClientNotFoundException.class, () -> clientService.removeClientWithId(clientId));
    }




}
