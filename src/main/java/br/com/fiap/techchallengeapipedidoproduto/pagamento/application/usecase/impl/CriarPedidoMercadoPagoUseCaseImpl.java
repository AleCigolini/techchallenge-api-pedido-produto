package br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.CriarPedidoMercadoPagoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.infrastructure.client.PagamentoClient;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;

public class CriarPedidoMercadoPagoUseCaseImpl implements CriarPedidoMercadoPagoUseCase {
    private final PagamentoClient pagamentoClient;

    public CriarPedidoMercadoPagoUseCaseImpl(PagamentoClient pagamentoClient) {
        this.pagamentoClient = pagamentoClient;
    }


    @Override
    public void criarPedidoMercadoPago(Pedido pedido) {
        try {
            pagamentoClient.criarPedidoMercadoPago(pedido);
//            MercadoPagoOrderRequest mercadoPagoOrderRequest = mercadoPagoOrderRequestMapper.pedidoParaMercadoPagoOrderItemRequest(pedido);
//
//            mercadoPagoCodigoQRClient.pedidosPresenciaisV2(
//                    mercadoPagoProperties.getUserId(),
//                    mercadoPagoProperties.getExternalStoreId(),
//                    mercadoPagoProperties.getExternalPosId(),
//                    mercadoPagoProperties.getAuthHeader(),
//                    mercadoPagoOrderRequest);
        } catch (Exception ex) {
            System.out.printf(
                    "MercadoPago Error. Status: %s, Content: %s",
                    ex.getCause(), ex.getMessage());
        }
    }
}