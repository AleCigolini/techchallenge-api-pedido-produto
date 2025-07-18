package br.com.fiap.techchallengeapipedidoproduto.cliente.presentation;

import br.com.fiap.techchallengeapipedidoproduto.cliente.common.domain.dto.request.ClienteRequestDTO;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ClienteEmailOuCpfValidatorTest {
    private final ClienteEmailOuCpfValidator validator = new ClienteEmailOuCpfValidator();
    private final ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);

    @Test
    public void deveSerValidoQuandoEmailInformado() {
        ClienteRequestDTO dto = mock(ClienteRequestDTO.class);
        when(dto.email()).thenReturn("email@teste.com");
        when(dto.cpf()).thenReturn("");
        assertTrue(validator.isValid(dto, context));
    }

    @Test
    public void deveSerValidoQuandoCpfInformado() {
        ClienteRequestDTO dto = mock(ClienteRequestDTO.class);
        when(dto.email()).thenReturn("");
        when(dto.cpf()).thenReturn("12345678900");
        assertTrue(validator.isValid(dto, context));
    }

    @Test
    public void deveSerInvalidoQuandoEmailECpfNaoInformados() {
        ClienteRequestDTO dto = mock(ClienteRequestDTO.class);
        when(dto.email()).thenReturn("");
        when(dto.cpf()).thenReturn("");
        assertFalse(validator.isValid(dto, context));
    }

    @Test
    public void deveSerInvalidoQuandoDtoForNulo() {
        assertFalse(validator.isValid(null, context));
    }
}

