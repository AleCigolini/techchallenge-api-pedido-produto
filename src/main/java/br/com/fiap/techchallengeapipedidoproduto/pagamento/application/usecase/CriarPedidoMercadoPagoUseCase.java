package br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase;


import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.CriarPedidoMercadoPagoRequestDto;

public interface CriarPedidoMercadoPagoUseCase {

    void criarPedidoMercadoPago(CriarPedidoMercadoPagoRequestDto pedido);
}
