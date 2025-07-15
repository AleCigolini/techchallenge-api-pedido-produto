package br.com.fiap.techchallengeapipedidoproduto.pagamento.common.domain.dto.request;

import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;

public record PagamentoPendenteRequestDTO(
        Pedido pedido,
        boolean criouPedidoMercadoPago
) {
}
