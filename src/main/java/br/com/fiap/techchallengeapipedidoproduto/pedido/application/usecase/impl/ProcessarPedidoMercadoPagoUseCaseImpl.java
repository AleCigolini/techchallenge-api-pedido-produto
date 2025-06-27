package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.core.config.properties.MercadoPagoProperties;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.SalvarPagamentoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.domain.StatusPagamentoEnum;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.ConsultarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.ProcessarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.SalvarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.WebhookNotificationRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.client.mercadopago.MercadoPagoMerchantOrdersClient;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.client.mercadopago.response.MercadoPagoMerchantOrderResponse;
import org.springframework.http.ResponseEntity;

import java.util.LinkedHashMap;

public class ProcessarPedidoMercadoPagoUseCaseImpl implements ProcessarPedidoUseCase {

    private final SalvarPagamentoUseCase salvarPagamentoUseCase;
    private final ConsultarPedidoUseCase consultarPedidoUseCase;
    private final SalvarPedidoUseCase salvarPedidoUseCase;
    private final MercadoPagoMerchantOrdersClient mercadoPagoMerchantOrdersClient;
    private final MercadoPagoProperties mercadoPagoProperties;

    public ProcessarPedidoMercadoPagoUseCaseImpl(
            SalvarPagamentoUseCase salvarPagamentoUseCase, ConsultarPedidoUseCase consultarPedidoUseCase,
            SalvarPedidoUseCase salvarPedidoUseCase,
            MercadoPagoMerchantOrdersClient mercadoPagoMerchantOrdersClient,
            MercadoPagoProperties mercadoPagoProperties
    ) {
        this.salvarPagamentoUseCase = salvarPagamentoUseCase;
        this.consultarPedidoUseCase = consultarPedidoUseCase;
        this.salvarPedidoUseCase = salvarPedidoUseCase;
        this.mercadoPagoMerchantOrdersClient = mercadoPagoMerchantOrdersClient;
        this.mercadoPagoProperties = mercadoPagoProperties;
    }

    @Override
    public void processarNotificacao(WebhookNotificationRequestDto notificacao) {

        try {
            ResponseEntity<Object> response =
                    mercadoPagoMerchantOrdersClient.obterPagamento(
                            notificacao.getId(),
                            mercadoPagoProperties.getAuthHeader());

            if (response.getStatusCode().is2xxSuccessful()) {

                var responseBody = (LinkedHashMap) response.getBody();

                MercadoPagoMerchantOrderResponse orderResponse = new MercadoPagoMerchantOrderResponse();

                for (Object key : responseBody.keySet()) {
                    if (key.equals("status")) {
                        orderResponse.setStatus((String) responseBody.get(key));

                    } else if (key.equals("id")) {
                        orderResponse.setId((Long) responseBody.get(key));

                    } else if (key.equals("external_reference")) {
                        orderResponse.setExternalReference((String) responseBody.get(key));
                    }
                }

                if (orderResponse.getStatus().equals("closed")) {
                    Pedido pedido = consultarPedidoUseCase.buscarPedidoPorId(orderResponse.getExternalReference());

                    Pagamento pagamento = new Pagamento();
                    pagamento.setPreco(pedido.getPreco());
                    pagamento.setCodigoPedido(pedido.getId());
                    pagamento.setStatus(StatusPagamentoEnum.APROVADO.toString());
                    salvarPagamentoUseCase.salvarPagamento(pagamento);

                    pedido.setStatus(StatusPedidoEnum.RECEBIDO.toString());
                    pedido.setCodigoPagamento(orderResponse.getId().toString());
                    salvarPedidoUseCase.atualizarPedido(pedido);
                }
            }

        } catch (Exception ex) {
            System.out.printf(
                    "MercadoPago Error. Status: %s, Content: %s",
                    ex.getCause(), ex.getMessage());
        }
    }
}