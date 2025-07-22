package br.com.fiap.techchallengeapipedidoproduto.core.validators.cpf;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class CpfValidatorTest {
    private CpfValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    void setUp() {
        validator = new CpfValidator();
        context = mock(ConstraintValidatorContext.class);
    }

    @Test
    void testIsValid_NullCpf_ReturnsTrue() {
        assertTrue(validator.isValid(null, context));
    }

    @Test
    void testIsValid_ValidCpf_ReturnsTrue() {
        // Assuming ValidadorCpf.isValidCPF("12345678909") returns true
        assertTrue(validator.isValid("12345678909", context));
    }

    @Test
    void testIsValid_InvalidCpf_ReturnsFalse() {
        // Assuming ValidadorCpf.isValidCPF("11111111111") returns false
        assertFalse(validator.isValid("11111111111", context));
    }
}

