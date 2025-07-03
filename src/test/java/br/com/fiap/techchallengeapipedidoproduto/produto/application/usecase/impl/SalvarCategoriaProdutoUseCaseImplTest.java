package br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.CategoriaProdutoGateway;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SalvarCategoriaProdutoUseCaseImplTest {

    private CategoriaProdutoGateway categoriaProdutoGateway;
    private SalvarCategoriaProdutoUseCaseImpl salvarCategoriaProdutoUseCase;

    @BeforeEach
    public void setUp() {
        categoriaProdutoGateway = Mockito.mock(CategoriaProdutoGateway.class);
        salvarCategoriaProdutoUseCase = new SalvarCategoriaProdutoUseCaseImpl(categoriaProdutoGateway);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(categoriaProdutoGateway);
    }

    @Test
    public void deveSalvarCategoriaProduto() {
        // given
        String id = UUID.randomUUID().toString();
        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setId(id);
        categoriaProduto.setNome("Bebidas");

        CategoriaProduto categoriaSalva = new CategoriaProduto();
        categoriaSalva.setId(id);
        categoriaSalva.setNome("Bebidas");

        when(categoriaProdutoGateway.salvarCategoriaProduto(categoriaProduto)).thenReturn(categoriaSalva);

        // when
        CategoriaProduto result = salvarCategoriaProdutoUseCase.salvarCategoriaProduto(categoriaProduto);

        // then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Bebidas", result.getNome());
        verify(categoriaProdutoGateway).salvarCategoriaProduto(categoriaProduto);
    }
}
