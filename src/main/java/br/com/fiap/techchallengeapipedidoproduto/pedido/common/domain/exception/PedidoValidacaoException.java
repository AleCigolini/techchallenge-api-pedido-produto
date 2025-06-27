package br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.exception;

import br.com.fiap.techchallengeapipedidoproduto.core.config.exception.exceptions.NegocioException;

public class PedidoValidacaoException extends NegocioException {

    public PedidoValidacaoException(String mensagem) {
        super(mensagem);
    }
}