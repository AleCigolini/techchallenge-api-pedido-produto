package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.core.config.properties.MercadoPagoProperties;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.SalvarPagamentoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.impl.ProcessarPedidoMercadoPagoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.ConsultarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.SalvarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.WebhookDataRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.WebhookNotificationRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.LinkedHashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ProcessarPedidoMercadoPagoUseCaseImplTest {

    private ConsultarPedidoUseCase consultarPedidoUseCase;
    private SalvarPedidoUseCase salvarPedidoUseCase;
    private MercadoPagoMerchantOrdersClient mercadoPagoMerchantOrdersClient;
    private MercadoPagoProperties mercadoPagoProperties;
    private ProcessarPedidoMercadoPagoUseCaseImpl processarPedidoMercadoPagoUseCase;

    @BeforeEach
    public void setUp() {
        consultarPedidoUseCase = Mockito.mock(ConsultarPedidoUseCase.class);
        salvarPedidoUseCase = Mockito.mock(SalvarPedidoUseCase.class);
        mercadoPagoMerchantOrdersClient = Mockito.mock(MercadoPagoMerchantOrdersClient.class);
        mercadoPagoProperties = Mockito.mock(MercadoPagoProperties.class);
        mercadoPagoProperties = Mockito.mock(MercadoPagoProperties.class);
        SalvarPagamentoUseCase salvarPagamentoUseCase = Mockito.mock(SalvarPagamentoUseCase.class);

        processarPedidoMercadoPagoUseCase = new ProcessarPedidoMercadoPagoUseCaseImpl(
                salvarPagamentoUseCase,
                consultarPedidoUseCase,
                salvarPedidoUseCase,
                mercadoPagoMerchantOrdersClient,
                mercadoPagoProperties
        );

        when(mercadoPagoProperties.getAuthHeader()).thenReturn("Bearer token123");
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset();
    }

    @Test
    public void deveProcessarNotificacaoQuandoPagamentoFechado() {
        // given
        WebhookNotificationRequestDto notificacao = new WebhookNotificationRequestDto();
        notificacao.setId("123456");
        notificacao.setType("merchant_order");
        notificacao.setAction("payment.created");
        notificacao.setUserId(Long.valueOf("123"));

        WebhookDataRequestDto dataRequestDto = new WebhookDataRequestDto();
        notificacao.setData(dataRequestDto);

        LinkedHashMap<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", "closed");
        responseBody.put("id", Long.valueOf("123"));
        responseBody.put("external_reference", "pedido-123");

        ResponseEntity<Object> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

        when(mercadoPagoMerchantOrdersClient.obterPagamento(notificacao.getId(), mercadoPagoProperties.getAuthHeader()))
                .thenReturn(responseEntity);

        Pedido pedido = new Pedido();
        pedido.setId("pedido-123");
        pedido.setCodigo("XYZ123");
        pedido.setPreco(new BigDecimal("79.90"));
        pedido.setStatus(StatusPedidoEnum.ABERTO.toString());

        when(consultarPedidoUseCase.buscarPedidoPorId("pedido-123")).thenReturn(pedido);
        when(salvarPedidoUseCase.atualizarPedido(any(Pedido.class))).thenReturn(pedido);

        // when
        processarPedidoMercadoPagoUseCase.processarNotificacao(notificacao);

        // then
        verify(mercadoPagoMerchantOrdersClient).obterPagamento(notificacao.getId(), mercadoPagoProperties.getAuthHeader());
        verify(consultarPedidoUseCase).buscarPedidoPorId("pedido-123");
        verify(salvarPedidoUseCase).atualizarPedido(any(Pedido.class));
    }

    @Test
    public void naoDeveProcessarQuandoStatusDiferenteDeClosed() {
        // given
        WebhookNotificationRequestDto notificacao = new WebhookNotificationRequestDto();
        notificacao.setId("123456");
        notificacao.setType("merchant_order");
        notificacao.setAction("payment.updated");

        LinkedHashMap<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", "pending");
        responseBody.put("id", Long.valueOf("123"));
        responseBody.put("external_reference", "pedido-123");

        ResponseEntity<Object> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

        when(mercadoPagoMerchantOrdersClient.obterPagamento(notificacao.getId(), mercadoPagoProperties.getAuthHeader()))
                .thenReturn(responseEntity);

        // when
        processarPedidoMercadoPagoUseCase.processarNotificacao(notificacao);

        // then
        verify(mercadoPagoMerchantOrdersClient).obterPagamento(notificacao.getId(), mercadoPagoProperties.getAuthHeader());
        verify(consultarPedidoUseCase, never()).buscarPedidoPorId(anyString());
        verify(salvarPedidoUseCase, never()).atualizarPedido(any(Pedido.class));
    }

    @Test
    public void naoDeveProcessarQuandoRespostaComErro() {
        // given
        WebhookNotificationRequestDto notificacao = new WebhookNotificationRequestDto();
        notificacao.setId("123456");

        ResponseEntity<Object> responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        when(mercadoPagoMerchantOrdersClient.obterPagamento(notificacao.getId(), mercadoPagoProperties.getAuthHeader()))
                .thenReturn(responseEntity);

        // when
        processarPedidoMercadoPagoUseCase.processarNotificacao(notificacao);

        // then
        verify(mercadoPagoMerchantOrdersClient).obterPagamento(notificacao.getId(), mercadoPagoProperties.getAuthHeader());
        verify(consultarPedidoUseCase, never()).buscarPedidoPorId(anyString());
        verify(salvarPedidoUseCase, never()).atualizarPedido(any(Pedido.class));
    }

    @Test
    public void deveCapturarExcecaoQuandoClienteFalhar() {
        // given
        WebhookNotificationRequestDto notificacao = new WebhookNotificationRequestDto();
        notificacao.setId("123456");

        doThrow(new RuntimeException("Erro de conexão"))
                .when(mercadoPagoMerchantOrdersClient).obterPagamento(anyString(), anyString());

        // when
        processarPedidoMercadoPagoUseCase.processarNotificacao(notificacao);

        // then
        verify(mercadoPagoMerchantOrdersClient).obterPagamento(notificacao.getId(), mercadoPagoProperties.getAuthHeader());
        verify(consultarPedidoUseCase, never()).buscarPedidoPorId(anyString());
        verify(salvarPedidoUseCase, never()).atualizarPedido(any(Pedido.class));
    }

    @Test
    public void deveAtualizarPedidoCorretamenteQuandoStatusFechado() {
        // given
        WebhookNotificationRequestDto notificacao = new WebhookNotificationRequestDto();
        notificacao.setId("123456");

        LinkedHashMap<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("status", "closed");
        responseBody.put("id", Long.valueOf("123"));
        responseBody.put("external_reference", "pedido-123");

        ResponseEntity<Object> responseEntity = new ResponseEntity<>(responseBody, HttpStatus.OK);

        when(mercadoPagoMerchantOrdersClient.obterPagamento(notificacao.getId(), mercadoPagoProperties.getAuthHeader()))
                .thenReturn(responseEntity);

        Pedido pedido = new Pedido();
        pedido.setId("pedido-123");
        pedido.setCodigo("XYZ123");
        pedido.setPreco(new BigDecimal("79.90"));
        pedido.setStatus(StatusPedidoEnum.ABERTO.toString());

        when(consultarPedidoUseCase.buscarPedidoPorId("pedido-123")).thenReturn(pedido);

        // when
        processarPedidoMercadoPagoUseCase.processarNotificacao(notificacao);

        // then
        verify(salvarPedidoUseCase).atualizarPedido(any(Pedido.class));

        Pedido pedidoCaptor = capturePedidoArgument();
        assert pedidoCaptor.getStatus().equals(StatusPedidoEnum.RECEBIDO.toString());
        assert pedidoCaptor.getCodigoPagamento().equals("123");
    }

    private Pedido capturePedidoArgument() {
        org.mockito.ArgumentCaptor<Pedido> pedidoCaptor = org.mockito.ArgumentCaptor.forClass(Pedido.class);
        verify(salvarPedidoUseCase).atualizarPedido(pedidoCaptor.capture());
        return pedidoCaptor.getValue();
    }
}
