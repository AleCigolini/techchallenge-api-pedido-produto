package br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway;


import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoGateway {

    List<Produto> buscarProdutos();

    Optional<Produto> buscarProdutoPorId(String id);

    Produto criarProduto(Produto produto);

    Produto atualizarProduto(Produto produto);

    void excluirProduto(String idProduto);

    List<Produto> buscarProdutosPorCategoria(String idCategoriaProduto);
}
