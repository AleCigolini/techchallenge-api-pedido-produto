package br.com.fiap.techchallengeapipedidoproduto.produto.common.exception;

import br.com.fiap.techchallengeapipedidoproduto.core.config.exception.exceptions.EntidadeNaoEncontradaException;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public ProdutoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public ProdutoNaoEncontradoException(Long idProduto) {
        this("Não existe um produto com ID:" + idProduto);
    }

}
