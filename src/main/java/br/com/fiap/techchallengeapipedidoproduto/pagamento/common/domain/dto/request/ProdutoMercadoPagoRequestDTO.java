package br.com.fiap.techchallengeapipedidoproduto.pagamento.common.domain.dto.request;

import java.math.BigDecimal;

public record ProdutoMercadoPagoRequestDTO(
        String codigoProduto,
        String nome,
        String descricao,
        String categoria,
        BigDecimal preco,
        String observacao,
        Long quantidade
) {}
