package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase;


import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;

public interface CriarPedidoMercadoPagoUseCase {

    boolean criarPedidoMercadoPago(Pedido pedido);
}
