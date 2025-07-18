package br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.infrastructure.client.PagamentoClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class ConsultarPagamentoUseCaseImplTest {
    private PagamentoClient pagamentoClient;
    private ConsultarPagamentoUseCaseImpl useCase;

    @BeforeEach
    public void setUp() {
        pagamentoClient = Mockito.mock(PagamentoClient.class);
        useCase = new ConsultarPagamentoUseCaseImpl(pagamentoClient);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(pagamentoClient);
    }

    @Test
    public void deveBuscarPagamentosPorPedido() {
        String pedidoId = "pedido123";
        Pagamento pagamento = new Pagamento();
        List<Pagamento> pagamentosMock = Collections.singletonList(pagamento);
        when(pagamentoClient.buscarPagamentosPorPedido(pedidoId)).thenReturn(ResponseEntity.ok(pagamentosMock));

        List<Pagamento> result = useCase.buscarPagamentosPorPedido(pedidoId);

        assertNotNull(result);
        assertEquals(pagamentosMock, result);
        Mockito.verify(pagamentoClient, Mockito.times(1)).buscarPagamentosPorPedido(pedidoId);
    }
}

