package br.com.fiap.techchallengeapipedidoproduto.pedido.domain;

import br.com.fiap.techchallengeapipedidoproduto.cliente.domain.Cliente;
import br.com.fiap.techchallengeapipedidoproduto.core.utils.domain.DominioBase;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.domain.Pagamento;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class Pedido extends DominioBase {

    private String id;
    private String codigo;
    private String status;
    private BigDecimal preco;
    private String codigoPagamento;
    private String observacao;
    private Cliente cliente;
    private List<ProdutoPedido> produtos;
    private List<Pagamento> pagamentos;
}