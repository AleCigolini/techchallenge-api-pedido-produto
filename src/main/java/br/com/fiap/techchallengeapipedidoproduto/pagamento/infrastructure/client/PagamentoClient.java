package br.com.fiap.techchallengeapipedidoproduto.pagamento.infrastructure.client;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.common.domain.dto.request.PagamentoPendenteRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.domain.Pagamento;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "PagamentoClient", url = "${client.pagamento.base-url}")
public interface PagamentoClient {

    @PostMapping("/salvar")
    ResponseEntity<Pagamento> salvarPagamento(@RequestBody Pagamento pagamento);

    @PostMapping("/salvar-pagamento-pendente")
    void criarPagamentoPendenteParaOPedido(@RequestBody PagamentoPendenteRequestDTO pagamentoPendenteRequestDTO);

    ResponseEntity<List<Pagamento>> buscarPagamentosPorPedido(@RequestBody String idPedido);
}