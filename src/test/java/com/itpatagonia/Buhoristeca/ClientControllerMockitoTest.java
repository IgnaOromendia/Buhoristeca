package com.itpatagonia.Buhoristeca;

import com.itpatagonia.Buhoristeca.controllers.ClientController;
import com.itpatagonia.Buhoristeca.dto.ClientDto;
import com.itpatagonia.Buhoristeca.dto.ClientRequestDto;
import com.itpatagonia.Buhoristeca.services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyChar;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientControllerMockitoTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    private ClientDto clientDto;

    @BeforeEach
    void setUp() {
        clientDto = mock(ClientDto.class);
    }

    @Test
    void registerNewClient_validRequest_returnsOk() {
        when(clientService.registerNewClient(any(ClientRequestDto.class))).thenReturn(clientDto);

        ClientRequestDto request = mock(ClientRequestDto.class);

        ResponseEntity<ClientDto> response = clientController.registerNewClient(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDto, response.getBody());
    }

    @Test
    void getActiveClients_validRequest_returnsOk() {
        when(clientService.getActiveClients()).thenReturn(List.of(clientDto));

        ResponseEntity<List<ClientDto>> response = clientController.getActiveClients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(clientDto), response.getBody());
    }

    @Test
    void removeClientWithId_validRequest_returnsOk() {
        when(clientService.removeClientWithId(1)).thenReturn(clientDto);

        ResponseEntity<ClientDto> response = clientController.removeClientWithId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDto, response.getBody());
    }

    @Test
    void activateClientWithId_validRequest_returnsOk() {
        when(clientService.activateClientWithId(1)).thenReturn(clientDto);

        ResponseEntity<ClientDto> response = clientController.activateClientWithId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clientDto, response.getBody());
    }





}
