package br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase;


import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;

import java.util.List;

public interface BuscarCategoriaProdutoUseCase {
    CategoriaProduto buscarCategoriaProdutoPorId(String id);
    List<CategoriaProduto> buscarCategoriasProduto();
}
