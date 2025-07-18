package br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request;

import br.com.fiap.techchallengeapipedidoproduto.cliente.common.domain.dto.request.ClienteRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class PedidoRequestDto {

    @NotBlank
    private String observacao;

    private ClienteRequestDTO cliente;

    @NotEmpty
    private List<ProdutoPedidoRequestDto> produtos;
}

