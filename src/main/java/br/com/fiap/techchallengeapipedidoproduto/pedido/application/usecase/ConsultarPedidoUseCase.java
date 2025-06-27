package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase;

import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;

import java.util.List;

public interface ConsultarPedidoUseCase {

    List<Pedido> buscarPedidos(List<StatusPedidoEnum> status);

    Pedido buscarPedidoPorId(String id);
}
