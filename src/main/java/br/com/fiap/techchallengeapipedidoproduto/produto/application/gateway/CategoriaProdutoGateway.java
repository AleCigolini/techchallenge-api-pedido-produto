package br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway;


import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;

import java.util.List;
import java.util.Optional;

public interface CategoriaProdutoGateway {

    CategoriaProduto salvarCategoriaProduto(CategoriaProduto categoriaProduto);
    Optional<CategoriaProduto> buscarCategoriaProdutoPorId(String id);
    List<CategoriaProduto> buscarCategoriasProduto();
}
