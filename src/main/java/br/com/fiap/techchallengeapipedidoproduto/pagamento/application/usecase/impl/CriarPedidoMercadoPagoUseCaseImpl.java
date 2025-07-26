package br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.CriarPedidoMercadoPagoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.infrastructure.client.PagamentoClient;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.common.domain.dto.request.CriarPedidoMercadoPagoRequestDTO;

public class CriarPedidoMercadoPagoUseCaseImpl implements CriarPedidoMercadoPagoUseCase {
    private final PagamentoClient pagamentoClient;

    public CriarPedidoMercadoPagoUseCaseImpl(PagamentoClient pagamentoClient) {
        this.pagamentoClient = pagamentoClient;
    }


    @Override
    public void criarPedidoMercadoPago(CriarPedidoMercadoPagoRequestDTO pedido) {
        try {
            pagamentoClient.criarPedido(pedido);
        } catch (Exception ex) {
            System.out.printf("Erro ao criar pedido Mercado Pago. Status: %s, Content: %s", ex.getCause(), ex.getMessage());
        }
    }
}