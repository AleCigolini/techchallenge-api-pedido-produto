package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase;

import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;

public interface SalvarPedidoUseCase {

    Pedido criarPedido(Pedido pedido, String idCliente);

    Pedido atualizarPedido(Pedido pedido);

    Pedido atualizarPedidoRecebido(String idPedido, String idPagamento);
}
