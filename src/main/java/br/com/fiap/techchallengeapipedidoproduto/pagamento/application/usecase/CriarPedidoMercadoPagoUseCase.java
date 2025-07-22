package br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase;


import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;

public interface CriarPedidoMercadoPagoUseCase {

    void criarPedidoMercadoPago(Pedido pedido);
}
