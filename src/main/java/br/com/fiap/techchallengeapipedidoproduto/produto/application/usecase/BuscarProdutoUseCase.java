package br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase;


import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;

import java.util.List;

public interface BuscarProdutoUseCase {
    List<Produto> buscarProdutos();
    Produto buscarProdutoPorId(String id);
    List<Produto> buscarProdutosPorCategoria(String idCategoriaProduto);
}
