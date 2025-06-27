package br.com.fiap.techchallengeapipedidoproduto.pedido.application.presenter;

import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.response.PedidoResponseDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;

import java.util.List;

public interface PedidoPresenter {

    List<PedidoResponseDto> pedidosParaPedidoResponseDTOs(List<Pedido> pedidos);

    List<StatusPedidoEnum> statusPedidoTextParaStatusPedidoEnums(List<String> status);

    List<StatusPedidoEnum> statusPedidoParaStatusPedidoEnums(List<String> status);

    StatusPedidoEnum statusPedidoParaStatusPedidoEnum(String status);

    PedidoResponseDto pedidoParaPedidoResponseDTO(Pedido pedido);

    Pedido pedidoRequestDtoParaPedido(PedidoResponseDto pedidoRequestDTO);
}
