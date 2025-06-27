package br.com.fiap.techchallengeapipedidoproduto.pedido.domain;

import br.com.fiap.techchallengeapipedidoproduto.core.utils.domain.DominioBase;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import lombok.Data;

@Data
public class ProdutoPedido extends DominioBase {

    private String id;
    private Long quantidade;
    private String observacao;
    private Produto produto;
}