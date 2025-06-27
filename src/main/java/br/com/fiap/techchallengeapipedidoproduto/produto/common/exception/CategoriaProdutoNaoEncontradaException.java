package br.com.fiap.techchallengeapipedidoproduto.produto.common.exception;


import br.com.fiap.techchallengeapipedidoproduto.core.config.exception.exceptions.EntidadeNaoEncontradaException;

public class CategoriaProdutoNaoEncontradaException extends EntidadeNaoEncontradaException {

    public CategoriaProdutoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public CategoriaProdutoNaoEncontradaException(Long categoriaProdutoId) {
        this(String.format("Não existe a categoria do produto para id: %d", categoriaProdutoId));
    }
}
