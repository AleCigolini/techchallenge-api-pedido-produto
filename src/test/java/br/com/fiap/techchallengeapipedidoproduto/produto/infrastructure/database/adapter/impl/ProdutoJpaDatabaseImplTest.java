package br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adapter.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaCategoriaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.repository.JpaProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProdutoJpaDatabaseImplTest {

    private JpaProdutoRepository jpaProdutoRepository;
    private ProdutoJpaDatabaseImpl produtoJpaDatabase;

    @BeforeEach
    public void setUp() {
        jpaProdutoRepository = Mockito.mock(JpaProdutoRepository.class);
        produtoJpaDatabase = new ProdutoJpaDatabaseImpl(jpaProdutoRepository);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(jpaProdutoRepository);
    }

    @Test
    public void deveBuscarTodosOsProdutosOrdenadosPorNome() {
        // given
        List<JpaProdutoEntity> produtosEsperados = Arrays.asList(
                criarJpaProdutoEntity(UUID.randomUUID(), "Refrigerante", "Refrigerante 350ml", new BigDecimal("7.50"), UUID.randomUUID()),
                criarJpaProdutoEntity(UUID.randomUUID(), "X-Burguer", "Hambúrguer com queijo", new BigDecimal("25.90"), UUID.randomUUID())
        );

        when(jpaProdutoRepository.findAllByOrderByNomeAsc()).thenReturn(produtosEsperados);

        // when
        List<JpaProdutoEntity> resultado = produtoJpaDatabase.findAllByOrderByNomeAsc();

        // then
        assertEquals(2, resultado.size());
        assertEquals(produtosEsperados, resultado);
        assertEquals("Refrigerante", resultado.get(0).getNome());
        assertEquals("X-Burguer", resultado.get(1).getNome());
        verify(jpaProdutoRepository).findAllByOrderByNomeAsc();
    }

    @Test
    public void deveBuscarProdutosPorCategoria() {
        // given
        UUID idCategoria = UUID.randomUUID();
        JpaCategoriaProdutoEntity categoria = new JpaCategoriaProdutoEntity();
        categoria.setId(idCategoria);
        categoria.setNome("Bebidas");

        List<JpaProdutoEntity> produtosEsperados = Arrays.asList(
                criarJpaProdutoEntity(UUID.randomUUID(), "Água Mineral", "Água mineral 500ml", new BigDecimal("4.50"), idCategoria),
                criarJpaProdutoEntity(UUID.randomUUID(), "Refrigerante", "Refrigerante 350ml", new BigDecimal("7.50"), idCategoria)
        );

        when(jpaProdutoRepository.findAllByCategoriaOrderByNomeAsc(categoria)).thenReturn(produtosEsperados);

        // when
        List<JpaProdutoEntity> resultado = produtoJpaDatabase.findAllByCategoriaOrderByNomeAsc(categoria);

        // then
        assertEquals(2, resultado.size());
        assertEquals(produtosEsperados, resultado);
        assertEquals("Água Mineral", resultado.get(0).getNome());
        assertEquals("Refrigerante", resultado.get(1).getNome());
        assertEquals(idCategoria, resultado.get(0).getCategoria().getId());
        assertEquals(idCategoria, resultado.get(1).getCategoria().getId());
        verify(jpaProdutoRepository).findAllByCategoriaOrderByNomeAsc(categoria);
    }

    @Test
    public void deveBuscarProdutoPorId() {
        // given
        UUID idProduto = UUID.randomUUID();
        UUID idCategoria = UUID.randomUUID();
        JpaProdutoEntity produto = criarJpaProdutoEntity(idProduto, "X-Burguer", "Hambúrguer com queijo", new BigDecimal("25.90"), idCategoria);

        when(jpaProdutoRepository.findById(idProduto)).thenReturn(Optional.of(produto));

        // when
        Optional<JpaProdutoEntity> resultado = produtoJpaDatabase.findById(idProduto);

        // then
        assertTrue(resultado.isPresent());
        assertEquals(produto, resultado.get());
        assertEquals(idProduto, resultado.get().getId());
        assertEquals("X-Burguer", resultado.get().getNome());
        assertEquals("Hambúrguer com queijo", resultado.get().getDescricao());
        assertEquals(new BigDecimal("25.90"), resultado.get().getPreco());
        assertEquals(idCategoria, resultado.get().getCategoria().getId());
        verify(jpaProdutoRepository).findById(idProduto);
    }

    @Test
    public void deveSalvarProduto() {
        // given
        UUID idProduto = UUID.randomUUID();
        UUID idCategoria = UUID.randomUUID();
        JpaProdutoEntity produto = criarJpaProdutoEntity(idProduto, "X-Burguer", "Hambúrguer com queijo", new BigDecimal("25.90"), idCategoria);

        when(jpaProdutoRepository.save(produto)).thenReturn(produto);

        // when
        JpaProdutoEntity resultado = produtoJpaDatabase.save(produto);

        // then
        assertNotNull(resultado);
        assertEquals(produto, resultado);
        assertEquals(idProduto, resultado.getId());
        assertEquals("X-Burguer", resultado.getNome());
        assertEquals("Hambúrguer com queijo", resultado.getDescricao());
        assertEquals(new BigDecimal("25.90"), resultado.getPreco());
        assertEquals(idCategoria, resultado.getCategoria().getId());
        verify(jpaProdutoRepository).save(produto);
    }

    @Test
    public void deveExcluirProduto() {
        // given
        UUID idProduto = UUID.randomUUID();
        doNothing().when(jpaProdutoRepository).deleteById(idProduto);

        // when
        produtoJpaDatabase.deleteById(idProduto);

        // then
        verify(jpaProdutoRepository).deleteById(idProduto);
    }

    private JpaProdutoEntity criarJpaProdutoEntity(UUID id, String nome, String descricao, BigDecimal preco, UUID idCategoria) {
        JpaProdutoEntity produto = new JpaProdutoEntity();
        produto.setId(id);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);

        JpaCategoriaProdutoEntity categoria = new JpaCategoriaProdutoEntity();
        categoria.setId(idCategoria);
        produto.setCategoria(categoria);

        return produto;
    }
}
