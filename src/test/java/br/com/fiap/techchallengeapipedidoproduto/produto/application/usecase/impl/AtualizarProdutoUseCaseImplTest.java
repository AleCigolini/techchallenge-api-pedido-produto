package br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.ProdutoGateway;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AtualizarProdutoUseCaseImplTest {

    private ProdutoGateway produtoGateway;
    private AtualizarProdutoUseCaseImpl atualizarProdutoUseCase;

    @BeforeEach
    public void setUp() {
        produtoGateway = Mockito.mock(ProdutoGateway.class);
        atualizarProdutoUseCase = new AtualizarProdutoUseCaseImpl(produtoGateway);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(produtoGateway);
    }

    @Test
    public void deveAtualizarProduto() {
        // given
        UUID id = UUID.randomUUID();
        Produto produto = new Produto();
        produto.setId(id.toString());
        produto.setNome("Refrigerante Atualizado");
        produto.setDescricao("Refrigerante de Cola Atualizado");
        produto.setPreco(BigDecimal.valueOf(6.0));

        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(id.toString());
        produtoAtualizado.setNome("Refrigerante Atualizado");
        produtoAtualizado.setDescricao("Refrigerante de Cola Atualizado");
        produtoAtualizado.setPreco(BigDecimal.valueOf(6.0));

        when(produtoGateway.atualizarProduto(produto)).thenReturn(produtoAtualizado);

        // when
        Produto result = atualizarProdutoUseCase.atualizarProduto(produto);

        // then
        assertNotNull(result);
        assertEquals(id.toString(), result.getId());
        assertEquals("Refrigerante Atualizado", result.getNome());
        assertEquals("Refrigerante de Cola Atualizado", result.getDescricao());
        assertEquals(BigDecimal.valueOf(6.0), result.getPreco());
        verify(produtoGateway).atualizarProduto(produto);
    }
}
