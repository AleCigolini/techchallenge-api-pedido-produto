package br.com.fiap.techchallengeapipedidoproduto.cliente.domain;

import lombok.Data;

@Data
public class Cliente {
    private String id;
    private String nome;
    private String cpf;
}
