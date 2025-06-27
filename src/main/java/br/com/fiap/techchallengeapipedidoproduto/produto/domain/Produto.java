package br.com.fiap.techchallengeapipedidoproduto.produto.domain;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class Produto {

    private String id;
    private String nome;
    private String descricao;
    private CategoriaProduto categoria;
    private BigDecimal preco;
    private OffsetDateTime dataCriacao;
    private Boolean ativo;
}
