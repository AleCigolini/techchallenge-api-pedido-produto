package br.com.fiap.techchallengeapipedidoproduto.cliente.presentation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = {ClienteEmailOuCpfValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailOuCpf {
    String message() default "É necessário fornecer pelo menos um dos campos: cpf";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
