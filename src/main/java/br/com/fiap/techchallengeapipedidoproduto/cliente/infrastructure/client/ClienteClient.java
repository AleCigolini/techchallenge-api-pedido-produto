package br.com.fiap.techchallengeapipedidoproduto.cliente.infrastructure.client;

import br.com.fiap.techchallengeapipedidoproduto.cliente.domain.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ClienteClient", url = "${client.cliente.base-url}")
public interface ClienteClient {

    @GetMapping("/cpf")
    ResponseEntity<Cliente> buscarClientePorCpf(@RequestHeader("cpf") String cpf);

    @GetMapping("/id")
    ResponseEntity<Cliente> buscarClientePorId(@RequestHeader("id") String id);
}