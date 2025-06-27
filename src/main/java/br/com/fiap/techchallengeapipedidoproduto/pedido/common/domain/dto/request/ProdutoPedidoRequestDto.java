package br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProdutoPedidoRequestDto {

    @NotBlank
    private String idProduto;

    @NotBlank
    private String observacao;

    @Min(1)
    private Long quantidade;
}
