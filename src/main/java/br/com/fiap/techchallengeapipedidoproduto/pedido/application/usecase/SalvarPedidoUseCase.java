package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase;

import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;

public interface SalvarPedidoUseCase {

    Pedido criarPedido(Pedido pedido);

    Pedido atualizarPedido(Pedido pedido);

    Pedido atualizarStatusPedido(StatusPedidoEnum statusPedidoEnum, String codigoPagamento, String id);
}
