package br.com.fiap.techchallengeapipedidoproduto.pedido.application.mapper.model;

import br.com.fiap.techchallengeapipedidoproduto.pedido.application.mapper.DatabasePedidoMapper;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.entity.JpaPedidoEntity;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DatabasePedidoModelMapper implements DatabasePedidoMapper {
    private ModelMapper modelMapper;

    public JpaPedidoEntity pedidoParaJpaPedidoEntity(Pedido pedido) {
        return modelMapper.map(pedido, JpaPedidoEntity.class);
    }

    public Pedido jpaPedidoEntityParaPedido(JpaPedidoEntity jpaPedidoEntity) {
        return modelMapper.map(jpaPedidoEntity, Pedido.class);
    }

    @Override
    public List<Pedido> jpaPedidosEntityParaPedidos(List<JpaPedidoEntity> jpaPedidoEntities) {
        return jpaPedidoEntities.stream().map(jpaPedidoEntity -> modelMapper.map(jpaPedidoEntity, Pedido.class)).toList();
    }
}