package br.com.fiap.techchallengeapipedidoproduto.cliente.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.cliente.domain.Cliente;
import br.com.fiap.techchallengeapipedidoproduto.cliente.infrastructure.client.ClienteClient;
import br.com.fiap.techchallengeapipedidoproduto.cliente.application.usecase.ConsultarClienteUseCase;

public class ConsultarClienteUseCaseImpl implements ConsultarClienteUseCase {
    private final ClienteClient clienteClient;

    public ConsultarClienteUseCaseImpl(ClienteClient clienteClient) {
        this.clienteClient = clienteClient;
    }

    @Override
    public Cliente buscarClientePorCpf(String cpf) {
        return clienteClient.buscarClientePorCpf(cpf).getBody();
    }

    @Override
    public Cliente buscarClientePorId(String id) {
        return clienteClient.buscarClientePorId(id).getBody();
    }
}
