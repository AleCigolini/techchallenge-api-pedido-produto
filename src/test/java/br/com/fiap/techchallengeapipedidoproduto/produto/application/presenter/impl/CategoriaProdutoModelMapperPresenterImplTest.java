package br.com.fiap.techchallengeapipedidoproduto.produto.application.presenter.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.CategoriaProdutoResponseDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoriaProdutoModelMapperPresenterImplTest {

    private ModelMapper modelMapper;
    private CategoriaProdutoModelMapperPresenterImpl presenter;

    @BeforeEach
    public void setUp() {
        modelMapper = Mockito.mock(ModelMapper.class);
        presenter = new CategoriaProdutoModelMapperPresenterImpl(modelMapper);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(modelMapper);
    }

    @Test
    public void deveConverterCategoriaProdutoParaCategoriaProdutoResponseDTO() {
        // given
        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setNome("Bebidas");
        CategoriaProdutoResponseDTO responseDTO = new CategoriaProdutoResponseDTO();
        responseDTO.setNome("Bebidas");

        when(modelMapper.map(categoriaProduto, CategoriaProdutoResponseDTO.class)).thenReturn(responseDTO);

        // when
        CategoriaProdutoResponseDTO result = presenter.categoriaProdutoParaCategoriaProdutoResponseDTO(categoriaProduto);

        // then
        assertNotNull(result);
        assertEquals("Bebidas", result.getNome());
        verify(modelMapper).map(categoriaProduto, CategoriaProdutoResponseDTO.class);
    }

    @Test
    public void deveConverterCategoriasProdutoParaCategoriasProdutoResponseDTO() {
        // given
        CategoriaProduto categoriaProduto1 = new CategoriaProduto();
        categoriaProduto1.setNome("Bebidas");
        CategoriaProduto categoriaProduto2 = new CategoriaProduto();
        categoriaProduto2.setNome("Lanches");

        List<CategoriaProduto> categoriasProduto = Arrays.asList(categoriaProduto1, categoriaProduto2);

        CategoriaProdutoResponseDTO responseDTO1 = new CategoriaProdutoResponseDTO();
        responseDTO1.setNome("Bebidas");
        CategoriaProdutoResponseDTO responseDTO2 = new CategoriaProdutoResponseDTO();
        responseDTO2.setNome("Lanches");

        when(modelMapper.map(categoriaProduto1, CategoriaProdutoResponseDTO.class)).thenReturn(responseDTO1);
        when(modelMapper.map(categoriaProduto2, CategoriaProdutoResponseDTO.class)).thenReturn(responseDTO2);

        // when
        List<CategoriaProdutoResponseDTO> result = presenter.categoriasProdutoParaCategoriasProdutoResponseDTO(categoriasProduto);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Bebidas", result.get(0).getNome());
        assertEquals("Lanches", result.get(1).getNome());
        verify(modelMapper).map(categoriaProduto1, CategoriaProdutoResponseDTO.class);
        verify(modelMapper).map(categoriaProduto2, CategoriaProdutoResponseDTO.class);
    }
}
