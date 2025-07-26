package br.com.fiap.techchallengeapipedidoproduto.pagamento.infrastructure.client;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.common.domain.dto.request.CriarPedidoMercadoPagoRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PagamentoClient", url = "${client.pagamento.base-url}")
public interface PagamentoClient {

    @PostMapping("/criar-pedido")
    ResponseEntity<Void> criarPedido(@RequestBody CriarPedidoMercadoPagoRequestDTO pedido);
}