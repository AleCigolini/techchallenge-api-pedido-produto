package br.com.fiap.techchallengeapipedidoproduto.pagamento.common.domain.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CriarPedidoMercadoPagoRequestDTO {
    private String codigoPedido;
    private String codigo;
    private BigDecimal preco;
    private UUID codigoCliente;
    private List<ProdutoMercadoPagoRequestDTO> produtos;
}

