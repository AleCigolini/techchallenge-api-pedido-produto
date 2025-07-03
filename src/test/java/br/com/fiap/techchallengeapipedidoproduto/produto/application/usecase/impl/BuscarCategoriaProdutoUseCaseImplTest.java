package br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.CategoriaProdutoGateway;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.exception.CategoriaProdutoNaoEncontradaException;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BuscarCategoriaProdutoUseCaseImplTest {

    private CategoriaProdutoGateway categoriaProdutoGateway;
    private BuscarCategoriaProdutoUseCaseImpl buscarCategoriaProdutoUseCase;

    @BeforeEach
    public void setUp() {
        categoriaProdutoGateway = Mockito.mock(CategoriaProdutoGateway.class);
        buscarCategoriaProdutoUseCase = new BuscarCategoriaProdutoUseCaseImpl(categoriaProdutoGateway);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(categoriaProdutoGateway);
    }

    @Test
    public void deveBuscarCategoriaProdutoPorId() {
        // given
        String id = UUID.randomUUID().toString();
        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setId(id);
        categoriaProduto.setNome("Bebidas");

        when(categoriaProdutoGateway.buscarCategoriaProdutoPorId(id)).thenReturn(Optional.of(categoriaProduto));

        // when
        CategoriaProduto result = buscarCategoriaProdutoUseCase.buscarCategoriaProdutoPorId(id);

        // then
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Bebidas", result.getNome());
        verify(categoriaProdutoGateway).buscarCategoriaProdutoPorId(id);
    }

    @Test
    public void deveLancarExcecaoQuandoCategoriaProdutoNaoForEncontradaPorId() {
        // given
        String id = UUID.randomUUID().toString();
        when(categoriaProdutoGateway.buscarCategoriaProdutoPorId(id)).thenReturn(Optional.empty());

        // when / then
        CategoriaProdutoNaoEncontradaException exception = assertThrows(CategoriaProdutoNaoEncontradaException.class, 
                () -> buscarCategoriaProdutoUseCase.buscarCategoriaProdutoPorId(id));

        assertEquals(id, exception.getMessage());
        verify(categoriaProdutoGateway).buscarCategoriaProdutoPorId(id);
    }

    @Test
    public void deveBuscarCategoriasProduto() {
        // given
        CategoriaProduto categoriaProduto1 = new CategoriaProduto();
        categoriaProduto1.setId(UUID.randomUUID().toString());
        categoriaProduto1.setNome("Bebidas");

        CategoriaProduto categoriaProduto2 = new CategoriaProduto();
        categoriaProduto2.setId(UUID.randomUUID().toString());
        categoriaProduto2.setNome("Lanches");

        List<CategoriaProduto> categoriasProduto = Arrays.asList(categoriaProduto1, categoriaProduto2);

        when(categoriaProdutoGateway.buscarCategoriasProduto()).thenReturn(categoriasProduto);

        // when
        List<CategoriaProduto> result = buscarCategoriaProdutoUseCase.buscarCategoriasProduto();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Bebidas", result.get(0).getNome());
        assertEquals("Lanches", result.get(1).getNome());
        verify(categoriaProdutoGateway).buscarCategoriasProduto();
    }
}
