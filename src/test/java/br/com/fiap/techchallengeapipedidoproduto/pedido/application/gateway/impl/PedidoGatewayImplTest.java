package br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway.impl;

import br.com.fiap.techchallengeapipedidoproduto.pedido.application.mapper.DatabasePedidoMapper;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.entity.JpaPedidoEntity;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.interfaces.PedidoDatabase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class PedidoGatewayImplTest {

    private PedidoDatabase pedidoDatabase;
    private DatabasePedidoMapper mapper;
    private PedidoGatewayImpl gateway;

    @BeforeEach
    public void setUp() {
        pedidoDatabase = Mockito.mock(PedidoDatabase.class);
        mapper = Mockito.mock(DatabasePedidoMapper.class);
        gateway = new PedidoGatewayImpl(pedidoDatabase, mapper);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(pedidoDatabase, mapper);
    }

    @Test
    public void deveBuscarTodosOsPedidos() {
        // given
        Mockito.when(pedidoDatabase.buscarPedidos(null)).thenReturn(Collections.emptyList());
        Mockito.when(mapper.jpaPedidosEntityParaPedidos(any())).thenReturn(Collections.emptyList());

        // when
        List<Pedido> result = gateway.buscarPedidos(null);

        // then
        assertNotNull(result);
        Mockito.verify(pedidoDatabase, Mockito.atLeastOnce()).buscarPedidos(null);
        Mockito.verify(mapper, Mockito.atLeastOnce()).jpaPedidosEntityParaPedidos(any());
    }

    @Test
    public void deveBuscarPedidoPorId() {
        // given
        String id = "1";
        Mockito.when(pedidoDatabase.buscarPedidoPorId(id)).thenReturn(Optional.of(new JpaPedidoEntity()));
        Mockito.when(mapper.jpaPedidoEntityParaPedido(any())).thenReturn(new Pedido());

        // when
        Pedido result = gateway.buscarPedidoPorId(id);

        // then
        assertNotNull(result);
        Mockito.verify(pedidoDatabase, Mockito.times(1)).buscarPedidoPorId(id);
        Mockito.verify(mapper, Mockito.times(1)).jpaPedidoEntityParaPedido(any());
    }

    @Test
    public void deveCriarPedido() {
        // given
        Pedido pedido = new Pedido();
        Mockito.when(mapper.pedidoParaJpaPedidoEntity(pedido)).thenReturn(new JpaPedidoEntity());
        Mockito.when(pedidoDatabase.criarPedido(any())).thenReturn(new JpaPedidoEntity());
        Mockito.when(mapper.jpaPedidoEntityParaPedido(any())).thenReturn(pedido);

        // when
        Pedido result = gateway.criarPedido(pedido);

        // then
        assertNotNull(result);
        Mockito.verify(mapper, Mockito.times(1)).pedidoParaJpaPedidoEntity(pedido);
        Mockito.verify(pedidoDatabase, Mockito.times(1)).criarPedido(any());
        Mockito.verify(mapper, Mockito.times(1)).jpaPedidoEntityParaPedido(any());
    }

    @Test
    public void deveSalvarPedido() {
        // given
        Pedido pedido = new Pedido();
        Mockito.when(mapper.pedidoParaJpaPedidoEntity(pedido)).thenReturn(new JpaPedidoEntity());
        Mockito.when(pedidoDatabase.salvarPedido(any())).thenReturn(new JpaPedidoEntity());
        Mockito.when(mapper.jpaPedidoEntityParaPedido(any())).thenReturn(pedido);

        // when
        Pedido result = gateway.salvarPedido(pedido);

        // then
        assertNotNull(result);
        Mockito.verify(mapper, Mockito.times(1)).pedidoParaJpaPedidoEntity(pedido);
        Mockito.verify(pedidoDatabase, Mockito.times(1)).salvarPedido(any());
        Mockito.verify(mapper, Mockito.times(1)).jpaPedidoEntityParaPedido(any());
    }
}
