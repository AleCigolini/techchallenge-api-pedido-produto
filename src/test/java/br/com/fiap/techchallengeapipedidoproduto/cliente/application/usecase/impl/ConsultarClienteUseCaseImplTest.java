package br.com.fiap.techchallengeapipedidoproduto.cliente.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.cliente.domain.Cliente;
import br.com.fiap.techchallengeapipedidoproduto.cliente.infrastructure.client.ClienteClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultarClienteUseCaseImplTest {

    private ClienteClient clienteClient;
    private ConsultarClienteUseCaseImpl consultarClienteUseCase;

    @BeforeEach
    public void setUp() {
        clienteClient = Mockito.mock(ClienteClient.class);
        consultarClienteUseCase = new ConsultarClienteUseCaseImpl(clienteClient);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset();
    }

    @Test
    public void deveBuscarClientePorId() {
        String id = "123";
        Cliente clienteMock = new Cliente();
        Mockito.when(clienteClient.buscarClientePorId(id)).thenReturn(ResponseEntity.ok(clienteMock));

        Cliente result = consultarClienteUseCase.buscarClientePorId(id);

        assertNotNull(result);
        assertEquals(clienteMock, result);
        Mockito.verify(clienteClient, Mockito.times(1)).buscarClientePorId(id);
    }

    @Test
    public void deveBuscarClientePorCpf() {
        String cpf = "11122233344";
        Cliente clienteMock = new Cliente();
        Mockito.when(clienteClient.buscarClientePorCpf(cpf)).thenReturn(ResponseEntity.ok(clienteMock));

        Cliente result = consultarClienteUseCase.buscarClientePorCpf(cpf);

        assertNotNull(result);
        assertEquals(clienteMock, result);
        Mockito.verify(clienteClient, Mockito.times(1)).buscarClientePorCpf(cpf);
    }
}
