package br.com.fiap.techchallengeapipedidoproduto.core.utils.domain;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class DominioBase {

    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataAtualizacao;
}
