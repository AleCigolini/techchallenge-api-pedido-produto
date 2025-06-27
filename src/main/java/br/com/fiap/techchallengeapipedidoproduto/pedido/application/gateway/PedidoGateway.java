package br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway;

import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;

import java.util.List;

public interface PedidoGateway {

    List<Pedido> buscarPedidos(List<StatusPedidoEnum> statusPedidoEnums);

    Pedido buscarPedidoPorId(String id);

    Pedido criarPedido(Pedido pedido);

    Pedido salvarPedido(Pedido pedido);
}