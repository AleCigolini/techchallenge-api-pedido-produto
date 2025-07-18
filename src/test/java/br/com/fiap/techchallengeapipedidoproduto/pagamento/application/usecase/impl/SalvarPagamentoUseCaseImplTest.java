package br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.common.domain.dto.request.PagamentoPendenteRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.infrastructure.client.PagamentoClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SalvarPagamentoUseCaseImplTest {
    private PagamentoClient pagamentoClient;
    private SalvarPagamentoUseCaseImpl useCase;

    @BeforeEach
    public void setUp() {
        pagamentoClient = Mockito.mock(PagamentoClient.class);
        useCase = new SalvarPagamentoUseCaseImpl(pagamentoClient);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(pagamentoClient);
    }

    @Test
    public void deveSalvarPagamento() {
        Pagamento pagamento = new Pagamento();
        useCase.salvarPagamento(pagamento);
        verify(pagamentoClient, times(1)).salvarPagamento(pagamento);
    }

    @Test
    public void deveCriarPagamentoPendenteParaOPedido() {
        PagamentoPendenteRequestDTO dto = Mockito.mock(PagamentoPendenteRequestDTO.class);
        useCase.criarPagamentoPendenteParaOPedido(dto);
        verify(pagamentoClient, times(1)).criarPagamentoPendenteParaOPedido(dto);
    }
}

