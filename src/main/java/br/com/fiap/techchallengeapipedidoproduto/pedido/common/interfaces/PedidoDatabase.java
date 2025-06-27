package br.com.fiap.techchallengeapipedidoproduto.pedido.common.interfaces;

import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.entity.JpaPedidoEntity;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;

import java.util.List;
import java.util.Optional;

public interface PedidoDatabase {

    List<JpaPedidoEntity> buscarPedidos(List<StatusPedidoEnum> statusPedidoEnums);

    JpaPedidoEntity criarPedido(JpaPedidoEntity jpaPedidoEntity);

    JpaPedidoEntity salvarPedido(JpaPedidoEntity jpaPedidoEntity);

    Optional<JpaPedidoEntity> buscarPedidoPorId(String id);
}
