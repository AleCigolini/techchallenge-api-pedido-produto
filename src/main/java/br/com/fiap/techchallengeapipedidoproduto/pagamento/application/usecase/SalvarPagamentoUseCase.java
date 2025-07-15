package br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.common.domain.dto.request.PagamentoPendenteRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.domain.Pagamento;

public interface SalvarPagamentoUseCase {

    void salvarPagamento(Pagamento pagamento);

    void criarPagamentoPendenteParaOPedido(PagamentoPendenteRequestDTO pagamentoPendenteRequestDTO);
}
