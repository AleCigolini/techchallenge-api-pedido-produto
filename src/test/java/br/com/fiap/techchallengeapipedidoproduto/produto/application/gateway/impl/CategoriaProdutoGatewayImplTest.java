package br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.CategoriaProdutoMapper;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaCategoriaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adapter.CategoriaProdutoDatabase;
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

public class CategoriaProdutoGatewayImplTest {

    private CategoriaProdutoDatabase categoriaProdutoDatabase;
    private CategoriaProdutoMapper categoriaProdutoMapper;
    private CategoriaProdutoGatewayImpl gateway;

    @BeforeEach
    public void setUp() {
        categoriaProdutoDatabase = Mockito.mock(CategoriaProdutoDatabase.class);
        categoriaProdutoMapper = Mockito.mock(CategoriaProdutoMapper.class);
        gateway = new CategoriaProdutoGatewayImpl(categoriaProdutoDatabase, categoriaProdutoMapper);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset();
    }

    @Test
    public void deveSalvarCategoriaProduto() {
        // given
        CategoriaProduto categoriaProduto = new CategoriaProduto();
        JpaCategoriaProdutoEntity jpaCategoriaProdutoEntity = new JpaCategoriaProdutoEntity();
        JpaCategoriaProdutoEntity savedEntity = new JpaCategoriaProdutoEntity();
        CategoriaProduto savedCategoriaProduto = new CategoriaProduto();

        Mockito.when(categoriaProdutoMapper.categoriaProdutoParaJpaCategoriaProdutoEntity(categoriaProduto))
                .thenReturn(jpaCategoriaProdutoEntity);
        Mockito.when(categoriaProdutoDatabase.save(jpaCategoriaProdutoEntity)).thenReturn(savedEntity);
        Mockito.when(categoriaProdutoMapper.jpaCategoriaProdutoEntityParaCategoriaProduto(savedEntity))
                .thenReturn(savedCategoriaProduto);

        // when
        CategoriaProduto result = gateway.salvarCategoriaProduto(categoriaProduto);

        // then
        assertNotNull(result);
        Mockito.verify(categoriaProdutoMapper, Mockito.times(1))
                .categoriaProdutoParaJpaCategoriaProdutoEntity(categoriaProduto);
        Mockito.verify(categoriaProdutoDatabase, Mockito.times(1)).save(jpaCategoriaProdutoEntity);
        Mockito.verify(categoriaProdutoMapper, Mockito.times(1))
                .jpaCategoriaProdutoEntityParaCategoriaProduto(savedEntity);
    }

    @Test
    public void deveBuscarCategoriaProdutoPorId() {
        // given
        String id = UUID.randomUUID().toString();
        UUID uuid = UUID.fromString(id);
        JpaCategoriaProdutoEntity jpaCategoriaProdutoEntity = new JpaCategoriaProdutoEntity();
        jpaCategoriaProdutoEntity.setAtivo(true);
        CategoriaProduto categoriaProduto = new CategoriaProduto();

        Mockito.when(categoriaProdutoDatabase.findById(uuid)).thenReturn(Optional.of(jpaCategoriaProdutoEntity));
        Mockito.when(categoriaProdutoMapper.jpaCategoriaProdutoEntityParaCategoriaProduto(jpaCategoriaProdutoEntity))
                .thenReturn(categoriaProduto);

        // when
        Optional<CategoriaProduto> result = gateway.buscarCategoriaProdutoPorId(id);

        // then
        assertTrue(result.isPresent());
        assertEquals(categoriaProduto, result.get());
        Mockito.verify(categoriaProdutoDatabase, Mockito.times(1)).findById(uuid);
        Mockito.verify(categoriaProdutoMapper, Mockito.times(1))
                .jpaCategoriaProdutoEntityParaCategoriaProduto(jpaCategoriaProdutoEntity);
    }

    @Test
    public void deveRetornarVazioQuandoBuscarCategoriaProdutoPorIdInexistente() {
        // given
        String id = UUID.randomUUID().toString();
        UUID uuid = UUID.fromString(id);

        Mockito.when(categoriaProdutoDatabase.findById(uuid)).thenReturn(Optional.empty());

        // when
        Optional<CategoriaProduto> result = gateway.buscarCategoriaProdutoPorId(id);

        // then
        assertFalse(result.isPresent());
        Mockito.verify(categoriaProdutoDatabase, Mockito.times(1)).findById(uuid);
        Mockito.verify(categoriaProdutoMapper, Mockito.never())
                .jpaCategoriaProdutoEntityParaCategoriaProduto(any());
    }

    @Test
    public void deveRetornarVazioQuandoBuscarCategoriaProdutoPorIdInativo() {
        // given
        String id = UUID.randomUUID().toString();
        UUID uuid = UUID.fromString(id);
        JpaCategoriaProdutoEntity jpaCategoriaProdutoEntity = new JpaCategoriaProdutoEntity();
        jpaCategoriaProdutoEntity.setAtivo(false);

        Mockito.when(categoriaProdutoDatabase.findById(uuid)).thenReturn(Optional.of(jpaCategoriaProdutoEntity));

        // when
        Optional<CategoriaProduto> result = gateway.buscarCategoriaProdutoPorId(id);

        // then
        assertFalse(result.isPresent());
        Mockito.verify(categoriaProdutoDatabase, Mockito.times(1)).findById(uuid);
        Mockito.verify(categoriaProdutoMapper, Mockito.never())
                .jpaCategoriaProdutoEntityParaCategoriaProduto(any());
    }

    @Test
    public void deveBuscarCategoriasProduto() {
        // given
        List<JpaCategoriaProdutoEntity> jpaCategorias = Arrays.asList(
                new JpaCategoriaProdutoEntity(),
                new JpaCategoriaProdutoEntity()
        );
        CategoriaProduto categoria1 = new CategoriaProduto();
        CategoriaProduto categoria2 = new CategoriaProduto();

        Mockito.when(categoriaProdutoDatabase.findAllByAtivoTrue()).thenReturn(jpaCategorias);
        Mockito.when(categoriaProdutoMapper.jpaCategoriaProdutoEntityParaCategoriaProduto(jpaCategorias.get(0)))
                .thenReturn(categoria1);
        Mockito.when(categoriaProdutoMapper.jpaCategoriaProdutoEntityParaCategoriaProduto(jpaCategorias.get(1)))
                .thenReturn(categoria2);

        // when
        List<CategoriaProduto> result = gateway.buscarCategoriasProduto();

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}

