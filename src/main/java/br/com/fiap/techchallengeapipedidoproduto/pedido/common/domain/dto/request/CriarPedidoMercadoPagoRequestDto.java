package br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request;

import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.ProdutoPedido;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CriarPedidoMercadoPagoRequestDto {
    private String codigoPedido;
    private String codigo;
    private BigDecimal preco;
    private UUID codigoCliente;
    private List<ProdutoPedido> produtos;
}

