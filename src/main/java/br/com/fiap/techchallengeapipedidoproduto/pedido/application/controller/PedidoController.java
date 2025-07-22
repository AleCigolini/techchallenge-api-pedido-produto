package br.com.fiap.techchallengeapipedidoproduto.pedido.application.controller;

import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoStatusRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.response.PedidoResponseDto;

import java.util.List;

public interface PedidoController {

    List<PedidoResponseDto> buscarPedidos(List<String> status);

    PedidoResponseDto criarPedido(PedidoRequestDto pedidoRequestDTO);

    PedidoResponseDto atualizarStatusPedido(PedidoStatusRequestDto pedidoStatusRequestDTO, String id);
}