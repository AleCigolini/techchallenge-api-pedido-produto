package br.com.fiap.techchallengeapipedidoproduto.core.validators.cpf.helper;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ValidadorCpfTest {
    @Test
    void testValidCpf() {
        // Valid CPF (not all digits the same, 11 digits)
        assertTrue(ValidadorCpf.isValidCPF("12345678909"));
    }

    @Test
    void testInvalidCpf_Length() {
        // Invalid CPF (less than 11 digits)
        assertFalse(ValidadorCpf.isValidCPF("1234567890"));
        // Invalid CPF (more than 11 digits)
        assertFalse(ValidadorCpf.isValidCPF("123456789012"));
    }

    @Test
    void testInvalidCpf_RepeatedDigits() {
        // Invalid CPF (all digits the same)
        assertFalse(ValidadorCpf.isValidCPF("11111111111"));
        assertFalse(ValidadorCpf.isValidCPF("00000000000"));
        assertFalse(ValidadorCpf.isValidCPF("99999999999"));
    }
}

