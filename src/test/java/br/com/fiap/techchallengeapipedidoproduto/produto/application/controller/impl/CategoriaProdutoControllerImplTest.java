package br.com.fiap.techchallengeapipedidoproduto.produto.application.controller.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.CategoriaProdutoMapper;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.presenter.CategoriaProdutoPresenter;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.BuscarCategoriaProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.SalvarCategoriaProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.CategoriaProdutoRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.CategoriaProdutoResponseDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adpater.CategoriaProdutoDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CategoriaProdutoControllerImplTest {

    private CategoriaProdutoControllerImpl controller;
    private ModelMapper modelMapper;
    private CategoriaProdutoDatabase categoriaProdutoDatabase;
    private SalvarCategoriaProdutoUseCase salvarCategoriaProdutoUseCase;
    private BuscarCategoriaProdutoUseCase buscarCategoriaProdutoUseCase;
    private CategoriaProdutoPresenter categoriaProdutoPresenter;
    private CategoriaProdutoMapper categoriaProdutoMapper;

    @BeforeEach
    public void setUp() {
        modelMapper = Mockito.mock(ModelMapper.class);
        categoriaProdutoDatabase = Mockito.mock(CategoriaProdutoDatabase.class);
        salvarCategoriaProdutoUseCase = Mockito.mock(SalvarCategoriaProdutoUseCase.class);
        buscarCategoriaProdutoUseCase = Mockito.mock(BuscarCategoriaProdutoUseCase.class);
        categoriaProdutoPresenter = Mockito.mock(CategoriaProdutoPresenter.class);
        categoriaProdutoMapper = Mockito.mock(CategoriaProdutoMapper.class);

        controller = new CategoriaProdutoControllerImpl(modelMapper, categoriaProdutoDatabase);

        ReflectionTestUtils.setField(controller, "salvarCategoriaProdutoUseCase", salvarCategoriaProdutoUseCase);
        ReflectionTestUtils.setField(controller, "buscarCategoriaProdutoUseCase", buscarCategoriaProdutoUseCase);
        ReflectionTestUtils.setField(controller, "categoriaProdutoPresenter", categoriaProdutoPresenter);
        ReflectionTestUtils.setField(controller, "categoriaProdutoMapper", categoriaProdutoMapper);
    }

    @Test
    public void deveBuscarCategoriaProdutoPorId() {
        // given
        String id = UUID.randomUUID().toString();
        CategoriaProduto categoriaEsperada = new CategoriaProduto();
        categoriaEsperada.setId(id);
        categoriaEsperada.setNome("Bebidas");

        when(buscarCategoriaProdutoUseCase.buscarCategoriaProdutoPorId(id)).thenReturn(categoriaEsperada);

        // when
        CategoriaProduto resultado = controller.buscarCategoriaProdutoPorId(id);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Bebidas", resultado.getNome());
        verify(buscarCategoriaProdutoUseCase, times(1)).buscarCategoriaProdutoPorId(id);
    }

    @Test
    public void deveBuscarTodasAsCategoriasProduto() {
        // given
        List<CategoriaProduto> categorias = Arrays.asList(
                criarCategoriaProduto("1", "Lanches"),
                criarCategoriaProduto("2", "Bebidas"),
                criarCategoriaProduto("3", "Sobremesas")
        );

        List<CategoriaProdutoResponseDTO> categoriasResponse = Arrays.asList(
                criarCategoriaProdutoResponseDTO("1", "Lanches"),
                criarCategoriaProdutoResponseDTO("2", "Bebidas"),
                criarCategoriaProdutoResponseDTO("3", "Sobremesas")
        );

        when(buscarCategoriaProdutoUseCase.buscarCategoriasProduto()).thenReturn(categorias);
        when(categoriaProdutoPresenter.categoriasProdutoParaCategoriasProdutoResponseDTO(categorias))
                .thenReturn(categoriasResponse);

        // when
        List<CategoriaProdutoResponseDTO> resultado = controller.buscarCategoriasProduto();

        // then
        assertNotNull(resultado);
        assertEquals(3, resultado.size());
        assertEquals("Lanches", resultado.get(0).getNome());
        assertEquals("Bebidas", resultado.get(1).getNome());
        assertEquals("Sobremesas", resultado.get(2).getNome());
        verify(buscarCategoriaProdutoUseCase, times(1)).buscarCategoriasProduto();
        verify(categoriaProdutoPresenter, times(1)).categoriasProdutoParaCategoriasProdutoResponseDTO(categorias);
    }

    @Test
    public void deveCriarCategoriaProduto() {
        // given
        String id = UUID.randomUUID().toString();
        CategoriaProdutoRequestDTO requestDTO = new CategoriaProdutoRequestDTO();
        requestDTO.setNome("Nova Categoria");

        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setNome("Nova Categoria");

        CategoriaProduto categoriaSalva = new CategoriaProduto();
        categoriaSalva.setId(id);
        categoriaSalva.setNome("Nova Categoria");

        CategoriaProdutoResponseDTO responseDTO = new CategoriaProdutoResponseDTO();
        responseDTO.setId(id);
        responseDTO.setNome("Nova Categoria");

        when(categoriaProdutoMapper.categoriaProdutoRequestDTOParaCategoriaProduto(requestDTO)).thenReturn(categoria);
        when(salvarCategoriaProdutoUseCase.salvarCategoriaProduto(categoria)).thenReturn(categoriaSalva);
        when(categoriaProdutoPresenter.categoriaProdutoParaCategoriaProdutoResponseDTO(categoriaSalva)).thenReturn(responseDTO);

        // when
        CategoriaProdutoResponseDTO resultado = controller.criarCategoriaProduto(requestDTO);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Nova Categoria", resultado.getNome());
        verify(categoriaProdutoMapper, times(1)).categoriaProdutoRequestDTOParaCategoriaProduto(requestDTO);
        verify(salvarCategoriaProdutoUseCase, times(1)).salvarCategoriaProduto(categoria);
        verify(categoriaProdutoPresenter, times(1)).categoriaProdutoParaCategoriaProdutoResponseDTO(categoriaSalva);
    }

    private CategoriaProduto criarCategoriaProduto(String id, String nome) {
        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setId(id);
        categoria.setNome(nome);
        return categoria;
    }

    private CategoriaProdutoResponseDTO criarCategoriaProdutoResponseDTO(String id, String nome) {
        CategoriaProdutoResponseDTO responseDTO = new CategoriaProdutoResponseDTO();
        responseDTO.setId(id);
        responseDTO.setNome(nome);
        return responseDTO;
    }
}
