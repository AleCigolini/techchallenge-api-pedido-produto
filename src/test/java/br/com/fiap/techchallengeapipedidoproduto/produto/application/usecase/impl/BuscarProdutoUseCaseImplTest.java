package br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.ProdutoGateway;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.exception.ProdutoNaoEncontradoException;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BuscarProdutoUseCaseImplTest {

    private ProdutoGateway produtoGateway;
    private BuscarProdutoUseCaseImpl buscarProdutoUseCase;

    @BeforeEach
    public void setUp() {
        produtoGateway = Mockito.mock(ProdutoGateway.class);
        buscarProdutoUseCase = new BuscarProdutoUseCaseImpl(produtoGateway);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(produtoGateway);
    }

    @Test
    public void deveBuscarProdutos() {
        // given
        Produto produto1 = new Produto();
        produto1.setId(UUID.randomUUID().toString());
        produto1.setNome("Refrigerante");
        produto1.setDescricao("Refrigerante de Cola");
        produto1.setPreco(new BigDecimal("5.0"));

        Produto produto2 = new Produto();
        produto2.setId(UUID.randomUUID().toString());
        produto2.setNome("Sanduíche");
        produto2.setDescricao("Sanduíche de Frango");
        produto2.setPreco(new BigDecimal("15.0"));

        List<Produto> produtos = Arrays.asList(produto1, produto2);

        when(produtoGateway.buscarProdutos()).thenReturn(produtos);

        // when
        List<Produto> result = buscarProdutoUseCase.buscarProdutos();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Refrigerante", result.get(0).getNome());
        assertEquals("Sanduíche", result.get(1).getNome());
        verify(produtoGateway).buscarProdutos();
    }

    @Test
    public void deveBuscarProdutoPorId() {
        // given
        String id = UUID.randomUUID().toString();
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome("Refrigerante");
        produto.setDescricao("Refrigerante de Cola");
        produto.setPreco(new BigDecimal("5.0"));

        when(produtoGateway.buscarProdutoPorId(id)).thenReturn(Optional.of(produto));

        // when
        Produto result = buscarProdutoUseCase.buscarProdutoPorId(id);

        // then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Refrigerante", result.getNome());
        assertEquals("Refrigerante de Cola", result.getDescricao());
        assertEquals(new BigDecimal("5.0"), result.getPreco());
        verify(produtoGateway).buscarProdutoPorId(id);
    }

    @Test
    public void deveLancarExcecaoQuandoProdutoNaoForEncontradoPorId() {
        // given
        String id = UUID.randomUUID().toString();
        when(produtoGateway.buscarProdutoPorId(id)).thenReturn(Optional.empty());

        // when / then
        ProdutoNaoEncontradoException exception = assertThrows(ProdutoNaoEncontradoException.class, 
                () -> buscarProdutoUseCase.buscarProdutoPorId(id));

        assertEquals(id, exception.getMessage());
        verify(produtoGateway).buscarProdutoPorId(id);
    }

    @Test
    public void deveBuscarProdutosPorCategoria() {
        // given
        String idCategoria = UUID.randomUUID().toString();

        Produto produto1 = new Produto();
        produto1.setId(UUID.randomUUID().toString());
        produto1.setNome("Refrigerante");
        produto1.setDescricao("Refrigerante de Cola");
        produto1.setPreco(new BigDecimal("5.0"));

        Produto produto2 = new Produto();
        produto2.setId(UUID.randomUUID().toString());
        produto2.setNome("Água Mineral");
        produto2.setDescricao("Água Mineral sem Gás");
        produto2.setPreco(new BigDecimal("3.0"));

        List<Produto> produtos = Arrays.asList(produto1, produto2);

        when(produtoGateway.buscarProdutosPorCategoria(idCategoria)).thenReturn(produtos);

        // when
        List<Produto> result = buscarProdutoUseCase.buscarProdutosPorCategoria(idCategoria);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Refrigerante", result.get(0).getNome());
        assertEquals("Água Mineral", result.get(1).getNome());
        verify(produtoGateway).buscarProdutosPorCategoria(idCategoria);
    }
}
