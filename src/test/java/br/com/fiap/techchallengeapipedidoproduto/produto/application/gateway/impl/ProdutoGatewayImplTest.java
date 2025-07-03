package br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.ProdutoMapper;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaCategoriaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adapter.ProdutoDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class ProdutoGatewayImplTest {

    private ProdutoDatabase produtoDatabase;
    private ProdutoMapper produtoMapper;
    private ProdutoGatewayImpl gateway;

    @BeforeEach
    public void setUp() {
        produtoDatabase = Mockito.mock(ProdutoDatabase.class);
        produtoMapper = Mockito.mock(ProdutoMapper.class);
        gateway = new ProdutoGatewayImpl(produtoDatabase, produtoMapper);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset();
    }

    @Test
    public void deveBuscarProdutos() {
        // given
        List<JpaProdutoEntity> jpaProdutos = Arrays.asList(
                new JpaProdutoEntity(),
                new JpaProdutoEntity()
        );
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();

        Mockito.when(produtoDatabase.findAllByOrderByNomeAsc()).thenReturn(jpaProdutos);
        Mockito.when(produtoMapper.jpaProdutoEntityParaProduto(jpaProdutos.get(0))).thenReturn(produto1);
        Mockito.when(produtoMapper.jpaProdutoEntityParaProduto(jpaProdutos.get(1))).thenReturn(produto2);

        // when
        List<Produto> result = gateway.buscarProdutos();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        Mockito.verify(produtoDatabase, Mockito.times(1)).findAllByOrderByNomeAsc();
    }

    @Test
    public void deveBuscarProdutoPorId() {
        // given
        String id = UUID.randomUUID().toString();
        UUID uuid = UUID.fromString(id);
        JpaProdutoEntity jpaProdutoEntity = new JpaProdutoEntity();
        Produto produto = new Produto();

        Mockito.when(produtoDatabase.findById(uuid)).thenReturn(Optional.of(jpaProdutoEntity));
        Mockito.when(produtoMapper.jpaProdutoEntityParaProduto(jpaProdutoEntity)).thenReturn(produto);

        // when
        Optional<Produto> result = gateway.buscarProdutoPorId(id);

        // then
        assertTrue(result.isPresent());
        assertEquals(produto, result.get());
        Mockito.verify(produtoDatabase, Mockito.times(1)).findById(uuid);
        Mockito.verify(produtoMapper, Mockito.times(1)).jpaProdutoEntityParaProduto(jpaProdutoEntity);
    }

    @Test
    public void deveRetornarVazioQuandoBuscarProdutoPorIdInexistente() {
        // given
        String id = UUID.randomUUID().toString();
        UUID uuid = UUID.fromString(id);

        Mockito.when(produtoDatabase.findById(uuid)).thenReturn(Optional.empty());

        // when
        Optional<Produto> result = gateway.buscarProdutoPorId(id);

        // then
        assertFalse(result.isPresent());
        Mockito.verify(produtoDatabase, Mockito.times(1)).findById(uuid);
        Mockito.verify(produtoMapper, Mockito.never()).jpaProdutoEntityParaProduto(any());
    }

    @Test
    public void deveCriarProduto() {
        // given
        Produto produto = new Produto();
        JpaProdutoEntity jpaProdutoEntity = new JpaProdutoEntity();
        JpaProdutoEntity savedEntity = new JpaProdutoEntity();
        Produto savedProduto = new Produto();

        Mockito.when(produtoMapper.produtoParaJpaProdutoEntity(produto)).thenReturn(jpaProdutoEntity);
        Mockito.when(produtoDatabase.save(jpaProdutoEntity)).thenReturn(savedEntity);
        Mockito.when(produtoMapper.jpaProdutoEntityParaProduto(savedEntity)).thenReturn(savedProduto);

        // when
        Produto result = gateway.criarProduto(produto);

        // then
        assertNotNull(result);
        Mockito.verify(produtoMapper, Mockito.times(1)).produtoParaJpaProdutoEntity(produto);
        Mockito.verify(produtoDatabase, Mockito.times(1)).save(jpaProdutoEntity);
        Mockito.verify(produtoMapper, Mockito.times(1)).jpaProdutoEntityParaProduto(savedEntity);
    }

    @Test
    public void deveAtualizarProduto() {
        // given
        Produto produto = new Produto();
        JpaProdutoEntity jpaProdutoEntity = new JpaProdutoEntity();
        JpaProdutoEntity updatedEntity = new JpaProdutoEntity();
        Produto updatedProduto = new Produto();

        Mockito.when(produtoMapper.produtoParaJpaProdutoEntity(produto)).thenReturn(jpaProdutoEntity);
        Mockito.when(produtoDatabase.save(jpaProdutoEntity)).thenReturn(updatedEntity);
        Mockito.when(produtoMapper.jpaProdutoEntityParaProduto(updatedEntity)).thenReturn(updatedProduto);

        // when
        Produto result = gateway.atualizarProduto(produto);

        // then
        assertNotNull(result);
        Mockito.verify(produtoMapper, Mockito.times(1)).produtoParaJpaProdutoEntity(produto);
        Mockito.verify(produtoDatabase, Mockito.times(1)).save(jpaProdutoEntity);
        Mockito.verify(produtoMapper, Mockito.times(1)).jpaProdutoEntityParaProduto(updatedEntity);
    }

    @Test
    public void deveExcluirProduto() {
        // given
        String id = UUID.randomUUID().toString();
        UUID uuid = UUID.fromString(id);

        // when
        gateway.excluirProduto(id);

        // then
        Mockito.verify(produtoDatabase, Mockito.times(1)).deleteById(uuid);
    }

    @Test
    public void deveBuscarProdutosPorCategoria() {
        // given
        String idCategoria = UUID.randomUUID().toString();
        UUID uuidCategoria = UUID.fromString(idCategoria);

        JpaCategoriaProdutoEntity categoria = new JpaCategoriaProdutoEntity();
        categoria.setId(uuidCategoria);

        List<JpaProdutoEntity> jpaProdutos = Arrays.asList(
                new JpaProdutoEntity(),
                new JpaProdutoEntity()
        );
        Produto produto1 = new Produto();
        Produto produto2 = new Produto();

        Mockito.when(produtoDatabase.findAllByCategoriaOrderByNomeAsc(any(JpaCategoriaProdutoEntity.class)))
                .thenReturn(jpaProdutos);
        Mockito.when(produtoMapper.jpaProdutoEntityParaProduto(jpaProdutos.get(0))).thenReturn(produto1);
        Mockito.when(produtoMapper.jpaProdutoEntityParaProduto(jpaProdutos.get(1))).thenReturn(produto2);

        // when
        List<Produto> result = gateway.buscarProdutosPorCategoria(idCategoria);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
