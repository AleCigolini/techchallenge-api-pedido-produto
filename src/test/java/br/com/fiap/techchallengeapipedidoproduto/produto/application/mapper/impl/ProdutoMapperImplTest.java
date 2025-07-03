package br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.ProdutoRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProdutoMapperImplTest {

    private ModelMapper modelMapper;
    private ProdutoMapperImpl produtoMapper;
    private TypeMap<ProdutoRequestDTO, Produto> typeMap;

    @BeforeEach
    public void setUp() {
        modelMapper = Mockito.mock(ModelMapper.class);
        typeMap = Mockito.mock(TypeMap.class);
        produtoMapper = new ProdutoMapperImpl(modelMapper);

        when(modelMapper.typeMap(ProdutoRequestDTO.class, Produto.class)).thenReturn(typeMap);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(modelMapper, typeMap);
    }

    @Test
    public void deveMapearProdutoRequestDtoParaProduto() {
        // given
        ProdutoRequestDTO produtoRequestDTO = new ProdutoRequestDTO();
        produtoRequestDTO.setNome("Refrigerante");
        produtoRequestDTO.setDescricao("Refrigerante de Cola");
        produtoRequestDTO.setPreco(BigDecimal.valueOf(5.0));

        Produto produto = new Produto();
        produto.setNome("Refrigerante");
        produto.setDescricao("Refrigerante de Cola");
        produto.setPreco(BigDecimal.valueOf(5.0));

        when(modelMapper.map(produtoRequestDTO, Produto.class)).thenReturn(produto);

        // when
        Produto result = produtoMapper.produtoRequestDtoParaProduto(produtoRequestDTO);

        // then
        assertNotNull(result);
        assertEquals("Refrigerante", result.getNome());
        assertEquals("Refrigerante de Cola", result.getDescricao());
        assertEquals(BigDecimal.valueOf(5.0), result.getPreco());
        verify(modelMapper, times(1)).map(produtoRequestDTO, Produto.class);
    }

    @Test
    public void deveMapearJpaProdutoEntityParaProduto() {
        // given
        UUID id = UUID.randomUUID();
        JpaProdutoEntity jpaProdutoEntity = new JpaProdutoEntity();
        jpaProdutoEntity.setId(id);
        jpaProdutoEntity.setNome("Refrigerante");
        jpaProdutoEntity.setDescricao("Refrigerante de Cola");
        jpaProdutoEntity.setPreco(BigDecimal.valueOf(5.0));

        Produto produto = new Produto();
        produto.setId(id.toString());
        produto.setNome("Refrigerante");
        produto.setDescricao("Refrigerante de Cola");
        produto.setPreco(BigDecimal.valueOf(5.0));

        when(modelMapper.map(jpaProdutoEntity, Produto.class)).thenReturn(produto);

        // when
        Produto result = produtoMapper.jpaProdutoEntityParaProduto(jpaProdutoEntity);

        // then
        assertNotNull(result);
        assertEquals(id.toString(), result.getId());
        assertEquals("Refrigerante", result.getNome());
        assertEquals("Refrigerante de Cola", result.getDescricao());
        assertEquals(BigDecimal.valueOf(5.0), result.getPreco());
        verify(modelMapper, times(1)).map(jpaProdutoEntity, Produto.class);
    }

    @Test
    public void deveMapearProdutoParaJpaProdutoEntity() {
        // given
        String idString = UUID.randomUUID().toString();
        Produto produto = new Produto();
        produto.setId(idString);
        produto.setNome("Refrigerante");
        produto.setDescricao("Refrigerante de Cola");
        produto.setPreco(BigDecimal.valueOf(5.0));

        JpaProdutoEntity jpaProdutoEntity = new JpaProdutoEntity();
        jpaProdutoEntity.setId(UUID.fromString(idString));
        jpaProdutoEntity.setNome("Refrigerante");
        jpaProdutoEntity.setDescricao("Refrigerante de Cola");
        jpaProdutoEntity.setPreco(BigDecimal.valueOf(5.0));

        when(modelMapper.map(produto, JpaProdutoEntity.class)).thenReturn(jpaProdutoEntity);

        // when
        JpaProdutoEntity result = produtoMapper.produtoParaJpaProdutoEntity(produto);

        // then
        assertNotNull(result);
        assertEquals(UUID.fromString(idString), result.getId());
        assertEquals("Refrigerante", result.getNome());
        assertEquals("Refrigerante de Cola", result.getDescricao());
        assertEquals(BigDecimal.valueOf(5.0), result.getPreco());
        verify(modelMapper, times(1)).map(produto, JpaProdutoEntity.class);
    }
}
