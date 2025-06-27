package br.com.fiap.techchallengeapipedidoproduto.pedido.application.presenter.impl;

import br.com.fiap.techchallengeapipedidoproduto.pedido.application.presenter.PedidoPresenter;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.response.PedidoResponseDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum.fromStatus;

@Component
@AllArgsConstructor
public class PedidoPresenterImpl implements PedidoPresenter {

    private final ModelMapper modelMapper;

    @Override
    public List<PedidoResponseDto> pedidosParaPedidoResponseDTOs(List<Pedido> pedidos) {
        return pedidos.stream().map(pedido -> modelMapper.map(pedido, PedidoResponseDto.class)).toList();
    }

    @Override
    public List<StatusPedidoEnum> statusPedidoTextParaStatusPedidoEnums(List<String> status) {
        return status.stream().map(StatusPedidoEnum::fromStringStatus).toList();
    }

    @Override
    public List<StatusPedidoEnum> statusPedidoParaStatusPedidoEnums(List<String> status) {
        return status.stream().map(StatusPedidoEnum::fromStatus).toList();
    }

    @Override
    public StatusPedidoEnum statusPedidoParaStatusPedidoEnum(String status) {
        return fromStatus(status);
    }

    @Override
    public PedidoResponseDto pedidoParaPedidoResponseDTO(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResponseDto.class);
    }

    @Override
    public Pedido pedidoRequestDtoParaPedido(PedidoResponseDto pedidoRequestDTO) {
        modelMapper.typeMap(PedidoResponseDto.class, Pedido.class).addMappings(mapper -> mapper.skip(Pedido::setId));

        return modelMapper.map(pedidoRequestDTO, Pedido.class);
    }
}
