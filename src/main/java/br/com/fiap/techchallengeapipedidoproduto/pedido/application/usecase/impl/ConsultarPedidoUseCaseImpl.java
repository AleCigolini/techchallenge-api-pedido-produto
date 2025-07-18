package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.cliente.domain.Cliente;
import br.com.fiap.techchallengeapipedidoproduto.cliente.application.usecase.ConsultarClienteUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.ConsultarPagamentoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.domain.Pagamento;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway.PedidoGateway;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.ConsultarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;

import java.util.List;

public class ConsultarPedidoUseCaseImpl implements ConsultarPedidoUseCase {

    private final PedidoGateway pedidoGateway;
    private final ConsultarClienteUseCase consultarClienteUseCase;
    private final ConsultarPagamentoUseCase consultarPagamentoUseCase;

    public ConsultarPedidoUseCaseImpl(PedidoGateway pedidoGateway, ConsultarClienteUseCase consultarClienteUseCase, ConsultarPagamentoUseCase consultarPagamentoUseCase) {
        this.pedidoGateway = pedidoGateway;
        this.consultarClienteUseCase = consultarClienteUseCase;
        this.consultarPagamentoUseCase = consultarPagamentoUseCase;
    }

    @Override
    public List<Pedido> buscarPedidos(List<StatusPedidoEnum> statusPedidoEnums) {
        List<Pedido> pedidos = pedidoGateway.buscarPedidos(statusPedidoEnums);
        pedidos.forEach(pedido -> {
            Cliente cliente = consultarClienteUseCase.buscarClientePorId(pedido.getCliente().getId());
            pedido.setCliente(cliente);

            List<Pagamento> pagamentos = consultarPagamentoUseCase.buscarPagamentosPorPedido(pedido.getId());
            pedido.setPagamentos(pagamentos);
        });

        return pedidos;
    }

    @Override
    public Pedido buscarPedidoPorId(String id) {
        Pedido pedido = pedidoGateway.buscarPedidoPorId(id);

        Cliente cliente = consultarClienteUseCase.buscarClientePorId(pedido.getCliente().getId());
        pedido.setCliente(cliente);

        List<Pagamento> pagamentos = consultarPagamentoUseCase.buscarPagamentosPorPedido(pedido.getId());
        pedido.setPagamentos(pagamentos);

        return pedido;
    }
}
