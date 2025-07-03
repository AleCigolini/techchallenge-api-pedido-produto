package br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.CategoriaProdutoRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaCategoriaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CategoriaProdutoMapperImplTest {

    private ModelMapper modelMapper;
    private CategoriaProdutoMapperImpl categoriaProdutoMapper;

    @BeforeEach
    public void setUp() {
        modelMapper = Mockito.mock(ModelMapper.class);
        categoriaProdutoMapper = new CategoriaProdutoMapperImpl(modelMapper);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset();
    }

    @Test
    public void deveMapearCategoriaProdutoRequestDTOParaCategoriaProduto() {
        // given
        CategoriaProdutoRequestDTO requestDTO = new CategoriaProdutoRequestDTO();
        requestDTO.setNome("Bebidas");

        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setNome("Bebidas");

        Mockito.when(modelMapper.map(requestDTO, CategoriaProduto.class)).thenReturn(categoriaProduto);

        // when
        CategoriaProduto result = categoriaProdutoMapper.categoriaProdutoRequestDTOParaCategoriaProduto(requestDTO);

        // then
        assertNotNull(result);
        assertEquals("Bebidas", result.getNome());
        Mockito.verify(modelMapper, Mockito.times(1)).map(requestDTO, CategoriaProduto.class);
    }

    @Test
    public void deveMapearCategoriaProdutoParaJpaCategoriaProdutoEntity() {
        // given
        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setId(UUID.randomUUID().toString());
        categoriaProduto.setNome("Bebidas");
        categoriaProduto.setAtivo(true);
        categoriaProduto.setDataCriacao(OffsetDateTime.now());
        categoriaProduto.setDataAtualizacao(OffsetDateTime.now());

        JpaCategoriaProdutoEntity jpaCategoriaProdutoEntity = new JpaCategoriaProdutoEntity();
        jpaCategoriaProdutoEntity.setId(UUID.fromString(categoriaProduto.getId()));
        jpaCategoriaProdutoEntity.setNome("Bebidas");
        jpaCategoriaProdutoEntity.setAtivo(true);
        jpaCategoriaProdutoEntity.setDataCriacao(categoriaProduto.getDataCriacao());
        jpaCategoriaProdutoEntity.setDataAtualizacao(categoriaProduto.getDataAtualizacao());

        Mockito.when(modelMapper.map(categoriaProduto, JpaCategoriaProdutoEntity.class)).thenReturn(jpaCategoriaProdutoEntity);

        // when
        JpaCategoriaProdutoEntity result = categoriaProdutoMapper.categoriaProdutoParaJpaCategoriaProdutoEntity(categoriaProduto);

        // then
        assertNotNull(result);
        assertEquals(UUID.fromString(categoriaProduto.getId()), result.getId());
        assertEquals("Bebidas", result.getNome());
        assertTrue(result.getAtivo());
        assertEquals(categoriaProduto.getDataCriacao(), result.getDataCriacao());
        assertEquals(categoriaProduto.getDataAtualizacao(), result.getDataAtualizacao());
        Mockito.verify(modelMapper, Mockito.times(1)).map(categoriaProduto, JpaCategoriaProdutoEntity.class);
    }

    @Test
    public void deveMapearJpaCategoriaProdutoEntityParaCategoriaProduto() {
        // given
        UUID id = UUID.randomUUID();
        JpaCategoriaProdutoEntity jpaCategoriaProdutoEntity = new JpaCategoriaProdutoEntity();
        jpaCategoriaProdutoEntity.setId(id);
        jpaCategoriaProdutoEntity.setNome("Bebidas");
        jpaCategoriaProdutoEntity.setAtivo(true);
        jpaCategoriaProdutoEntity.setDataCriacao(OffsetDateTime.now());
        jpaCategoriaProdutoEntity.setDataAtualizacao(OffsetDateTime.now());

        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setId(id.toString());
        categoriaProduto.setNome("Bebidas");
        categoriaProduto.setAtivo(true);
        categoriaProduto.setDataCriacao(jpaCategoriaProdutoEntity.getDataCriacao());
        categoriaProduto.setDataAtualizacao(jpaCategoriaProdutoEntity.getDataAtualizacao());

        Mockito.when(modelMapper.map(jpaCategoriaProdutoEntity, CategoriaProduto.class)).thenReturn(categoriaProduto);

        // when
        CategoriaProduto result = categoriaProdutoMapper.jpaCategoriaProdutoEntityParaCategoriaProduto(jpaCategoriaProdutoEntity);

        // then
        assertNotNull(result);
        assertEquals(id.toString(), result.getId());
        assertEquals("Bebidas", result.getNome());
        assertTrue(result.getAtivo());
        assertEquals(jpaCategoriaProdutoEntity.getDataCriacao(), result.getDataCriacao());
        assertEquals(jpaCategoriaProdutoEntity.getDataAtualizacao(), result.getDataAtualizacao());
        Mockito.verify(modelMapper, Mockito.times(1)).map(jpaCategoriaProdutoEntity, CategoriaProduto.class);
    }
}
