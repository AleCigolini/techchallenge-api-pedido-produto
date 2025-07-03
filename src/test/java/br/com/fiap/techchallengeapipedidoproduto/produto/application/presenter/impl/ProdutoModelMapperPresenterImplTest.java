package br.com.fiap.techchallengeapipedidoproduto.produto.application.presenter.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.ProdutoResponseDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
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

public class ProdutoModelMapperPresenterImplTest {

    private ModelMapper modelMapper;
    private ProdutoModelMapperPresenterImpl presenter;

    @BeforeEach
    public void setUp() {
        modelMapper = Mockito.mock(ModelMapper.class);
        presenter = new ProdutoModelMapperPresenterImpl(modelMapper);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(modelMapper);
    }

    @Test
    public void deveConverterProdutoParaProdutoResponseDTO() {
        // given
        Produto produto = new Produto();
        produto.setNome("Refrigerante");
        produto.setDescricao("Refrigerante de Cola");

        ProdutoResponseDTO responseDTO = new ProdutoResponseDTO();
        responseDTO.setNome("Refrigerante");
        responseDTO.setDescricao("Refrigerante de Cola");

        when(modelMapper.map(produto, ProdutoResponseDTO.class)).thenReturn(responseDTO);

        // when
        ProdutoResponseDTO result = presenter.produtoParaProdutoResponseDTO(produto);

        // then
        assertNotNull(result);
        assertEquals("Refrigerante", result.getNome());
        assertEquals("Refrigerante de Cola", result.getDescricao());
        verify(modelMapper).map(produto, ProdutoResponseDTO.class);
    }

    @Test
    public void deveConverterProdutosParaProdutosResponseDTO() {
        // given
        Produto produto1 = new Produto();
        produto1.setNome("Refrigerante");
        produto1.setDescricao("Refrigerante de Cola");

        Produto produto2 = new Produto();
        produto2.setNome("Sanduíche");
        produto2.setDescricao("Sanduíche de Frango");

        List<Produto> produtos = Arrays.asList(produto1, produto2);

        ProdutoResponseDTO responseDTO1 = new ProdutoResponseDTO();
        responseDTO1.setNome("Refrigerante");
        responseDTO1.setDescricao("Refrigerante de Cola");

        ProdutoResponseDTO responseDTO2 = new ProdutoResponseDTO();
        responseDTO2.setNome("Sanduíche");
        responseDTO2.setDescricao("Sanduíche de Frango");

        when(modelMapper.map(produto1, ProdutoResponseDTO.class)).thenReturn(responseDTO1);
        when(modelMapper.map(produto2, ProdutoResponseDTO.class)).thenReturn(responseDTO2);

        // when
        List<ProdutoResponseDTO> result = presenter.produtosParaProdutosResponseDTO(produtos);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Refrigerante", result.get(0).getNome());
        assertEquals("Refrigerante de Cola", result.get(0).getDescricao());
        assertEquals("Sanduíche", result.get(1).getNome());
        assertEquals("Sanduíche de Frango", result.get(1).getDescricao());
        verify(modelMapper).map(produto1, ProdutoResponseDTO.class);
        verify(modelMapper).map(produto2, ProdutoResponseDTO.class);
    }
}
