package br.com.fiap.techchallengeapipedidoproduto.cliente.application.usecase;

import br.com.fiap.techchallengeapipedidoproduto.cliente.domain.Cliente;

public interface ConsultarClienteUseCase {

    Cliente buscarClientePorCpf(String cpf);
    Cliente buscarClientePorId(String id);
}
