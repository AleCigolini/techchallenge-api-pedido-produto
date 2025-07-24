package br.com.fiap.techchallengeapipedidoproduto.pagamento.infrastructure.client;

import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.CriarPedidoMercadoPagoRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PagamentoClient", url = "${client.pagamento.base-url}")
public interface PagamentoClient {

    @PostMapping("/criar-pedido")
    ResponseEntity<Void> criarPedido(@RequestBody CriarPedidoMercadoPagoRequestDto pedido);
}

//public class ProdutoRequestDto {
//    private String codigoProduto;
//    private String nome;
//    private String descricao;
//    private String categoria;
//    private BigDecimal preco;
//    private String observacao;
//    private Long quantidade;
//}
