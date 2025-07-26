package br.com.fiap.techchallengeapipedidoproduto.pedido.application.mapper;

import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;

public interface RequestPedidoMapper {

    Pedido pedidoRequestDtoParaPedido(PedidoRequestDto pedidoRequestDto);
}