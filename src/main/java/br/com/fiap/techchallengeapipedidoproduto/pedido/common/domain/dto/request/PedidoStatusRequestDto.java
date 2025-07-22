package br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PedidoStatusRequestDto {

    @NotBlank
    private String status;

    private String codigoPagamento;
}