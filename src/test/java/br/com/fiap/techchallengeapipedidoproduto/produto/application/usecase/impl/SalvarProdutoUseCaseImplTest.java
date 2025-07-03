package br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.ProdutoGateway;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
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

public class SalvarProdutoUseCaseImplTest {

    private ProdutoGateway produtoGateway;
    private SalvarProdutoUseCaseImpl salvarProdutoUseCase;

    @BeforeEach
    public void setUp() {
        produtoGateway = Mockito.mock(ProdutoGateway.class);
        salvarProdutoUseCase = new SalvarProdutoUseCaseImpl(produtoGateway);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(produtoGateway);
    }

    @Test
    public void deveCriarProduto() {
        // given
        String id = UUID.randomUUID().toString();
        String idCategoria = UUID.randomUUID().toString();
        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setId(idCategoria);

        Produto produto = new Produto();
        produto.setNome("X-Burguer");
        produto.setDescricao("Hambúrguer com queijo e molho especial");
        produto.setPreco(new BigDecimal("25.90"));
        produto.setCategoria(categoriaProduto);

        Produto produtoCriado = new Produto();
        produtoCriado.setId(id);
        produtoCriado.setNome("X-Burguer");
        produtoCriado.setDescricao("Hambúrguer com queijo e molho especial");
        produtoCriado.setPreco(new BigDecimal("25.90"));
        produtoCriado.setCategoria(categoriaProduto);

        when(produtoGateway.criarProduto(produto)).thenReturn(produtoCriado);

        // when
        Produto result = salvarProdutoUseCase.criarProduto(produto);

        // then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("X-Burguer", result.getNome());
        assertEquals("Hambúrguer com queijo e molho especial", result.getDescricao());
        assertEquals(new BigDecimal("25.90"), result.getPreco());
        assertEquals(idCategoria, result.getCategoria().getId());
        verify(produtoGateway).criarProduto(produto);
    }
}
