package br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adapter.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaCategoriaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.repository.JpaCategoriaProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoriaProdutoJpaDatabaseImplTest {

    private JpaCategoriaProdutoRepository jpaCategoriaProdutoRepository;
    private CategoriaProdutoJpaDatabaseImpl categoriaProdutoJpaDatabase;

    @BeforeEach
    public void setUp() {
        jpaCategoriaProdutoRepository = Mockito.mock(JpaCategoriaProdutoRepository.class);
        categoriaProdutoJpaDatabase = new CategoriaProdutoJpaDatabaseImpl(jpaCategoriaProdutoRepository);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(jpaCategoriaProdutoRepository);
    }

    @Test
    public void deveSalvarCategoriaProduto() {
        // given
        UUID id = UUID.randomUUID();
        JpaCategoriaProdutoEntity categoria = new JpaCategoriaProdutoEntity();
        categoria.setId(id);
        categoria.setNome("Bebidas");
        categoria.setAtivo(Boolean.TRUE);
        categoria.setDataCriacao(OffsetDateTime.now());
        categoria.setDataAtualizacao(OffsetDateTime.now());

        when(jpaCategoriaProdutoRepository.save(categoria)).thenReturn(categoria);

        // when
        JpaCategoriaProdutoEntity resultado = categoriaProdutoJpaDatabase.save(categoria);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Bebidas", resultado.getNome());
        assertTrue(resultado.getAtivo());
        verify(jpaCategoriaProdutoRepository).save(categoria);
    }

    @Test
    public void deveBuscarCategoriaProdutoPorId() {
        // given
        UUID id = UUID.randomUUID();
        JpaCategoriaProdutoEntity categoria = new JpaCategoriaProdutoEntity();
        categoria.setId(id);
        categoria.setNome("Bebidas");
        categoria.setAtivo(Boolean.TRUE);
        categoria.setDataCriacao(OffsetDateTime.now());
        categoria.setDataAtualizacao(OffsetDateTime.now());

        when(jpaCategoriaProdutoRepository.findById(id)).thenReturn(Optional.of(categoria));

        // when
        Optional<JpaCategoriaProdutoEntity> resultado = categoriaProdutoJpaDatabase.findById(id);

        // then
        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getId());
        assertEquals("Bebidas", resultado.get().getNome());
        assertTrue(resultado.get().getAtivo());
        verify(jpaCategoriaProdutoRepository).findById(id);
    }

    @Test
    public void deveBuscarTodasAsCategoriasAtivas() {
        // given
        UUID id1 = UUID.randomUUID();
        JpaCategoriaProdutoEntity categoria1 = new JpaCategoriaProdutoEntity();
        categoria1.setId(id1);
        categoria1.setNome("Bebidas");
        categoria1.setAtivo(Boolean.TRUE);

        UUID id2 = UUID.randomUUID();
        JpaCategoriaProdutoEntity categoria2 = new JpaCategoriaProdutoEntity();
        categoria2.setId(id2);
        categoria2.setNome("Lanches");
        categoria2.setAtivo(Boolean.TRUE);

        List<JpaCategoriaProdutoEntity> categorias = Arrays.asList(categoria1, categoria2);

        when(jpaCategoriaProdutoRepository.findAllByAtivoTrue()).thenReturn(categorias);

        // when
        List<JpaCategoriaProdutoEntity> resultado = categoriaProdutoJpaDatabase.findAllByAtivoTrue();

        // then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Bebidas", resultado.get(0).getNome());
        assertEquals("Lanches", resultado.get(1).getNome());
        verify(jpaCategoriaProdutoRepository).findAllByAtivoTrue();
    }
}
