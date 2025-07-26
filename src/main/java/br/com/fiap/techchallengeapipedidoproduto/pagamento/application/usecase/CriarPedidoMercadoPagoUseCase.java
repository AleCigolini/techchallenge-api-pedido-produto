package br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase;


import br.com.fiap.techchallengeapipedidoproduto.pagamento.common.domain.dto.request.CriarPedidoMercadoPagoRequestDTO;

public interface CriarPedidoMercadoPagoUseCase {

    void criarPedidoMercadoPago(CriarPedidoMercadoPagoRequestDTO pedido);
}
