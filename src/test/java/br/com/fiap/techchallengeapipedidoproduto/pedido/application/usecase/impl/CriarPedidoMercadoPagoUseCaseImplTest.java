package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.core.config.properties.MercadoPagoProperties;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.client.mercadopago.MercadoPagoCodigoQRClient;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.client.mercadopago.mapper.MercadoPagoOrderRequestMapper;

import java.math.BigDecimal;
import java.util.List;

import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.client.mercadopago.request.MercadoPagoOrderItemRequest;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.client.mercadopago.request.MercadoPagoOrderRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CriarPedidoMercadoPagoUseCaseImplTest {

    private MercadoPagoOrderRequestMapper mercadoPagoOrderRequestMapper;
    private MercadoPagoCodigoQRClient mercadoPagoCodigoQRClient;
    private MercadoPagoProperties mercadoPagoProperties;
    private CriarPedidoMercadoPagoUseCaseImpl useCase;

    @BeforeEach
    public void setUp() {
        mercadoPagoOrderRequestMapper = Mockito.mock(MercadoPagoOrderRequestMapper.class);
        mercadoPagoCodigoQRClient = Mockito.mock(MercadoPagoCodigoQRClient.class);
        mercadoPagoProperties = Mockito.mock(MercadoPagoProperties.class);
        useCase = new CriarPedidoMercadoPagoUseCaseImpl(
                mercadoPagoOrderRequestMapper,
                mercadoPagoCodigoQRClient,
                mercadoPagoProperties
        );

        when(mercadoPagoProperties.getUserId()).thenReturn(Long.valueOf("123"));
        when(mercadoPagoProperties.getExternalStoreId()).thenReturn("store123");
        when(mercadoPagoProperties.getExternalPosId()).thenReturn("pos123");
        when(mercadoPagoProperties.getAuthHeader()).thenReturn("Bearer token123");
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(mercadoPagoOrderRequestMapper, mercadoPagoCodigoQRClient, mercadoPagoProperties);
    }

    @Test
    public void deveCriarPedidoMercadoPagoComSucesso() {
        // given
        Pedido pedido = new Pedido();
        MercadoPagoOrderRequest orderRequest = MercadoPagoOrderRequest.builder()
                .externalReference("pedido-123")
                .title("Pedido #123")
                .description("Pedido de teste para o MercadoPago")
                .notificationUrl("https://api-pedidos.fiap.com.br/webhook/mercadopago")
                .totalAmount(new BigDecimal("79.90"))
                .items(List.of(MercadoPagoOrderItemRequest.builder()
                        .skuNumber("prod-001")
                        .category("food")
                        .title("Hambúrguer Especial")
                        .description("Hambúrguer artesanal com cheddar e bacon")
                        .unitPrice(new BigDecimal("39.90"))
                        .quantity(Long.valueOf("2"))
                        .unitMeasure("unit")
                        .totalAmount(new BigDecimal("79.80"))
                        .build()))
                .build();
        when(mercadoPagoOrderRequestMapper.pedidoParaMercadoPagoOrderItemRequest(pedido)).thenReturn(orderRequest);

        // when
        boolean result = useCase.criarPedidoMercadoPago(pedido);

        // then
        assertTrue(result);
        verify(mercadoPagoOrderRequestMapper).pedidoParaMercadoPagoOrderItemRequest(pedido);
        verify(mercadoPagoCodigoQRClient).pedidosPresenciaisV2(
                mercadoPagoProperties.getUserId(),
                mercadoPagoProperties.getExternalStoreId(),
                mercadoPagoProperties.getExternalPosId(),
                mercadoPagoProperties.getAuthHeader(),
                orderRequest
        );
    }

    @Test
    public void deveRetornarFalsoQuandoOcorrerErroAoCriarPedidoMercadoPago() {
        // given
        Pedido pedido = new Pedido();
        MercadoPagoOrderRequest orderRequest = MercadoPagoOrderRequest.builder()
                .externalReference("pedido-456")
                .title("Pedido #456")
                .description("Pedido de teste para o MercadoPago com erro")
                .notificationUrl("https://api-pedidos.fiap.com.br/webhook/mercadopago")
                .totalAmount(new BigDecimal("129.90"))
                .items(List.of(MercadoPagoOrderItemRequest.builder()
                        .skuNumber("prod-002")
                        .category("food")
                        .title("Pizza Grande")
                        .description("Pizza grande 4 queijos")
                        .unitPrice(new BigDecimal("64.95"))
                        .quantity(Long.valueOf("2"))
                        .unitMeasure("unit")
                        .totalAmount(new BigDecimal("129.90"))
                        .build()))
                .build();
        when(mercadoPagoOrderRequestMapper.pedidoParaMercadoPagoOrderItemRequest(pedido)).thenReturn(orderRequest);
        doThrow(new RuntimeException("Erro ao criar pedido no MercadoPago"))
                .when(mercadoPagoCodigoQRClient).pedidosPresenciaisV2(any(), anyString(), anyString(), anyString(), any(MercadoPagoOrderRequest.class));

        // when
        boolean result = useCase.criarPedidoMercadoPago(pedido);

        // then
        assertFalse(result);
        verify(mercadoPagoOrderRequestMapper).pedidoParaMercadoPagoOrderItemRequest(pedido);
    }

    @Test
    public void deveRetornarFalsoQuandoOcorrerErroNoMapper() {
        // given
        Pedido pedido = new Pedido();
        when(mercadoPagoOrderRequestMapper.pedidoParaMercadoPagoOrderItemRequest(pedido))
                .thenThrow(new RuntimeException("Erro no mapper"));

        // when
        boolean result = useCase.criarPedidoMercadoPago(pedido);

        // then
        assertFalse(result);
    }
}

