package br.com.fiap.techchallengeapipedidoproduto.pedido.domain;

import br.com.fiap.techchallengeapipedidoproduto.core.utils.domain.DominioBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class Pedido extends DominioBase {

    private String id;
    private String codigo;
    private String status;
    private BigDecimal preco;
    private String observacao;
    private UUID idCliente;
    private List<ProdutoPedido> produtos;
    private UUID idPagamento;
}