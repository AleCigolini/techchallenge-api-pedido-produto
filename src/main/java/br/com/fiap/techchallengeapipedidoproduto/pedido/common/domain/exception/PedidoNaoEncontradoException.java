package br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.exception;

import br.com.fiap.techchallengeapipedidoproduto.core.config.exception.exceptions.EntidadeNaoEncontradaException;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public PedidoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}
