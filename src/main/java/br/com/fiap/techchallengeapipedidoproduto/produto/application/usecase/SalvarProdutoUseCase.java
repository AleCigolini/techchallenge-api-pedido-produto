package br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase;


import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;

public interface SalvarProdutoUseCase {
    Produto criarProduto(Produto produto);
}
