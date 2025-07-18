package br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.ConsultarPagamentoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.infrastructure.client.PagamentoClient;

import java.util.List;

public class ConsultarPagamentoUseCaseImpl implements ConsultarPagamentoUseCase {

    private final PagamentoClient pagamentoClient;

    public ConsultarPagamentoUseCaseImpl(PagamentoClient pagamentoClient) {
        this.pagamentoClient = pagamentoClient;
    }

    @Override
    public List<Pagamento> buscarPagamentosPorPedido(String idPedido) {
        return pagamentoClient.buscarPagamentosPorPedido(idPedido).getBody();
    }
}
