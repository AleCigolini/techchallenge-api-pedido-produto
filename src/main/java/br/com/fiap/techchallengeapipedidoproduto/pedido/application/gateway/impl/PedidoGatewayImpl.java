package br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway.impl;

import br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway.PedidoGateway;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.mapper.DatabasePedidoMapper;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.interfaces.PedidoDatabase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class PedidoGatewayImpl implements PedidoGateway {

    private PedidoDatabase pedidoDatabase;
    private DatabasePedidoMapper mapper;

    @Override
    public List<Pedido> buscarPedidos(List<StatusPedidoEnum> statusPedidoEnums) {
        return mapper.jpaPedidosEntityParaPedidos(pedidoDatabase.buscarPedidos(statusPedidoEnums));
    }

    @Override
    public Pedido buscarPedidoPorId(String id) {
        return mapper.jpaPedidoEntityParaPedido(pedidoDatabase.buscarPedidoPorId(id).orElse(null));
    }

    @Override
    public Pedido criarPedido(Pedido pedido) {
        final var jpaPedidoEntity = mapper.pedidoParaJpaPedidoEntity(pedido);
        return mapper.jpaPedidoEntityParaPedido(pedidoDatabase.criarPedido(jpaPedidoEntity));
    }

    @Override
    public Pedido salvarPedido(Pedido pedido) {
        final var jpaPedidoEntity = mapper.pedidoParaJpaPedidoEntity(pedido);
        return mapper.jpaPedidoEntityParaPedido(pedidoDatabase.salvarPedido(jpaPedidoEntity));
    }
}
