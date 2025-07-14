package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.cliente.domain.Cliente;
import br.com.fiap.techchallengeapipedidoproduto.cliente.usecase.ConsultarClienteUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway.PedidoGateway;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.ConsultarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;

import java.util.List;

public class ConsultarPedidoUseCaseImpl implements ConsultarPedidoUseCase {

    private final PedidoGateway pedidoGateway;
    private final ConsultarClienteUseCase consultarClienteUseCase;

    public ConsultarPedidoUseCaseImpl(PedidoGateway pedidoGateway, ConsultarClienteUseCase consultarClienteUseCase) {
        this.pedidoGateway = pedidoGateway;
        this.consultarClienteUseCase = consultarClienteUseCase;
    }

    @Override
    public List<Pedido> buscarPedidos(List<StatusPedidoEnum> statusPedidoEnums) {
        List<Pedido> pedidos = pedidoGateway.buscarPedidos(statusPedidoEnums);
        pedidos.forEach(pedido -> {
            Cliente cliente = consultarClienteUseCase.buscarClientePorId(pedido.getCliente().getId());
            pedido.setCliente(cliente);
        });

        return pedidos;
    }

    @Override
    public Pedido buscarPedidoPorId(String id) {
        return pedidoGateway.buscarPedidoPorId(id);
    }
}
