package br.com.fiap.techchallengeapipedidoproduto.cliente.common.domain.dto.request;

import br.com.fiap.techchallengeapipedidoproduto.core.validators.cpf.Cpf;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(
        @Size(max = 255, message = "O nome deve ter {max} caracteres") String nome,
        @Size(max = 255, message = "O e-mail deve ter {max} caracteres") String email,
        @Cpf String cpf
) {
}
