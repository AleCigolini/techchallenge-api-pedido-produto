package br.com.fiap.techchallengeapipedidoproduto.core.validators.cpf;

import br.com.fiap.techchallengeapipedidoproduto.core.validators.cpf.helper.ValidadorCpf;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CpfValidator implements ConstraintValidator<Cpf, String> {
    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {
        return cpf == null || ValidadorCpf.isValidCPF(cpf);
    }
}
