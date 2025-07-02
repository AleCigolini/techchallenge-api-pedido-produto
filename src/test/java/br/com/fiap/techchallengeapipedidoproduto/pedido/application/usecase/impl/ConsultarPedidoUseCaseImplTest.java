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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        List<Pedido> pedidosMock = Collections.singletonList(new Pedido());
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
        List<Pedido> pedidosMock = Arrays.asList(new Pedido(), new Pedido());
        when(pedidoGateway.buscarPedidos(statusFiltro)).thenReturn(pedidosMock);

        // when
        List<Pedido> result = useCase.buscarPedidos(statusFiltro);

        // then
        assertNotNull(result);
        assertEquals(pedidosMock, result);
        assertEquals(2, result.size());
        Mockito.verify(pedidoGateway, Mockito.times(1)).buscarPedidos(statusFiltro);
    }

    @Test
    public void deveBuscarPedidoPorId() {
        // given
        String id = "abc123";
        Pedido pedidoMock = new Pedido();
        pedidoMock.setId(id);
        when(pedidoGateway.buscarPedidoPorId(id)).thenReturn(pedidoMock);

        // when
        Pedido result = useCase.buscarPedidoPorId(id);

        // then
        assertNotNull(result);
        assertEquals(id, result.getId());
        Mockito.verify(pedidoGateway, Mockito.times(1)).buscarPedidoPorId(id);
    }
}


