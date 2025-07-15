package br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.SalvarPagamentoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.common.domain.dto.request.PagamentoPendenteRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.infrastructure.client.PagamentoClient;

public class SalvarPagamentoUseCaseImpl implements SalvarPagamentoUseCase {

    private final PagamentoClient pagamentoClient;

    public SalvarPagamentoUseCaseImpl(PagamentoClient pagamentoClient) {
        this.pagamentoClient = pagamentoClient;
    }

    @Override
    public void salvarPagamento(Pagamento pagamento) {
        pagamentoClient.salvarPagamento(pagamento);
    }

    @Override
    public void criarPagamentoPendenteParaOPedido(PagamentoPendenteRequestDTO pagamentoPendenteRequestDTO) {
        pagamentoClient.criarPagamentoPendenteParaOPedido(pagamentoPendenteRequestDTO);
    }
}
