package br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.domain.Pagamento;

import java.util.List;

public interface ConsultarPagamentoUseCase {
    List<Pagamento> buscarPagamentosPorPedido(String idPedido);
}
