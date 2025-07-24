package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway.PedidoGateway;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ConsultarPedidoUseCaseImplTest {

    private PedidoGateway pedidoGateway;
    private ConsultarPedidoUseCaseImpl useCase;

    @BeforeEach
    public void setUp() {
        pedidoGateway = Mockito.mock(PedidoGateway.class);
        useCase = new ConsultarPedidoUseCaseImpl(pedidoGateway);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset();
    }

    @Test
    public void deveBuscarTodosOsPedidos() {
        // given
        Pedido pedido = new Pedido();
        pedido.setIdCliente(UUID.randomUUID());
        List<Pedido> pedidosMock = Collections.singletonList(pedido);
        when(pedidoGateway.buscarPedidos(null)).thenReturn(pedidosMock);

        // when
        List<Pedido> result = useCase.buscarPedidos(null);

        // then
        assertNotNull(result);
        assertEquals(pedidosMock, result);
        Mockito.verify(pedidoGateway, Mockito.times(1)).buscarPedidos(null);
    }

    @Test
    public void deveBuscarPedidosFiltradosPorStatus() {
        // given
        List<StatusPedidoEnum> statusFiltro = Arrays.asList(StatusPedidoEnum.ABERTO, StatusPedidoEnum.EM_PREPARACAO);
        Pedido pedido = new Pedido();
        pedido.setIdCliente(UUID.randomUUID());
        List<Pedido> pedidosMock = List.of(pedido);
        when(pedidoGateway.buscarPedidos(statusFiltro)).thenReturn(pedidosMock);

        // when
        List<Pedido> result = useCase.buscarPedidos(statusFiltro);

        // then
        assertNotNull(result);
        assertEquals(pedidosMock, result);
        assertEquals(1, result.size());
        Mockito.verify(pedidoGateway, Mockito.times(1)).buscarPedidos(statusFiltro);
    }

    @Test
    public void deveBuscarPedidoPorId() {
        // given
        String id = "abc123";
        Pedido pedidoMock = new Pedido();
        pedidoMock.setId(id);
        pedidoMock.setIdCliente(UUID.randomUUID());
        when(pedidoGateway.buscarPedidoPorId(id)).thenReturn(pedidoMock);

        // when
        Pedido result = useCase.buscarPedidoPorId(id);

        // then
        assertNotNull(result);
        assertEquals(id, result.getId());
        Mockito.verify(pedidoGateway, Mockito.times(1)).buscarPedidoPorId(id);
    }
}


