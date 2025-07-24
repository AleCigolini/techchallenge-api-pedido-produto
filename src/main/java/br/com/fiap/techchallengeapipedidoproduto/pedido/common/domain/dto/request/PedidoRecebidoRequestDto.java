package br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PedidoRecebidoRequestDto {
    @NotBlank
    private String codigoPagamento;
}