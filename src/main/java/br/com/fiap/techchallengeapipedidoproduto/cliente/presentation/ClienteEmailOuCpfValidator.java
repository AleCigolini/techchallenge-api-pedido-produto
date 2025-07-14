package br.com.fiap.techchallengeapipedidoproduto.cliente.presentation;

import br.com.fiap.techchallengeapipedidoproduto.cliente.common.domain.dto.request.ClienteRequestDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ClienteEmailOuCpfValidator implements ConstraintValidator<EmailOuCpf, ClienteRequestDTO> {
    @Override
    public boolean isValid(ClienteRequestDTO clienteRequestDto, ConstraintValidatorContext context) {
        return clienteRequestDto != null &&
                ((clienteRequestDto.email() != null && !clienteRequestDto.email().isBlank()) ||
                        (clienteRequestDto.cpf() != null && !clienteRequestDto.cpf().isBlank()));
    }
}
