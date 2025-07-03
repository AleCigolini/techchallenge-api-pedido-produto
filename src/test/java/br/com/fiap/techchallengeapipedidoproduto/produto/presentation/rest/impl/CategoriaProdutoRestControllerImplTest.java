package br.com.fiap.techchallengeapipedidoproduto.produto.presentation.rest.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.controller.CategoriaProdutoController;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.CategoriaProdutoRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.CategoriaProdutoResponseDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoriaProdutoRestControllerImplTest {

    private CategoriaProdutoController categoriaProdutoController;
    private CategoriaProdutoRestControllerImpl restController;

    @BeforeEach
    public void setUp() {
        categoriaProdutoController = Mockito.mock(CategoriaProdutoController.class);
        restController = new CategoriaProdutoRestControllerImpl(categoriaProdutoController);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(categoriaProdutoController);
    }

    @Test
    public void deveBuscarCategoriaProdutoPorIdRetornandoResponseOk() {
        // given
        String id = UUID.randomUUID().toString();
        CategoriaProduto categoriaProduto = new CategoriaProduto();
        categoriaProduto.setId(id);
        categoriaProduto.setNome("Bebidas");
        categoriaProduto.setDataCriacao(OffsetDateTime.now());
        categoriaProduto.setDataAtualizacao(OffsetDateTime.now());
        categoriaProduto.setAtivo(true);

        when(categoriaProdutoController.buscarCategoriaProdutoPorId(id)).thenReturn(categoriaProduto);

        // when
        ResponseEntity<CategoriaProduto> response = restController.buscarCategoriaProduto(id);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals("Bebidas", response.getBody().getNome());
        verify(categoriaProdutoController).buscarCategoriaProdutoPorId(id);
    }

    @Test
    public void deveBuscarTodasAsCategoriasRetornandoResponseOk() {
        // given
        List<CategoriaProdutoResponseDTO> categoriasEsperadas = Arrays.asList(
                criarCategoriaProdutoResponseDTO("1", "Lanches"),
                criarCategoriaProdutoResponseDTO("2", "Bebidas"),
                criarCategoriaProdutoResponseDTO("3", "Sobremesas")
        );

        when(categoriaProdutoController.buscarCategoriasProduto()).thenReturn(categoriasEsperadas);

        // when
        ResponseEntity<List<CategoriaProdutoResponseDTO>> response = restController.buscarCategoriasProduto();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
        assertEquals("Lanches", response.getBody().get(0).getNome());
        assertEquals("Bebidas", response.getBody().get(1).getNome());
        assertEquals("Sobremesas", response.getBody().get(2).getNome());
        verify(categoriaProdutoController).buscarCategoriasProduto();
    }

    @Test
    public void deveCriarCategoriaProdutoRetornandoCreated() throws URISyntaxException {
        // given
        String id = UUID.randomUUID().toString();
        CategoriaProdutoRequestDTO requestDTO = new CategoriaProdutoRequestDTO();
        requestDTO.setNome("Nova Categoria");

        CategoriaProdutoResponseDTO responseDTO = new CategoriaProdutoResponseDTO();
        responseDTO.setId(id);
        responseDTO.setNome("Nova Categoria");
        responseDTO.setDataCriacao(OffsetDateTime.now());
        responseDTO.setDataAtualizacao(OffsetDateTime.now());

        when(categoriaProdutoController.criarCategoriaProduto(requestDTO)).thenReturn(responseDTO);

        // when
        ResponseEntity<CategoriaProdutoResponseDTO> response = restController.criarCategoriaProduto(requestDTO);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals("Nova Categoria", response.getBody().getNome());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals("/categorias-produto/" + id, response.getHeaders().getLocation().getPath());
        verify(categoriaProdutoController).criarCategoriaProduto(requestDTO);
    }

    private CategoriaProdutoResponseDTO criarCategoriaProdutoResponseDTO(String id, String nome) {
        CategoriaProdutoResponseDTO responseDTO = new CategoriaProdutoResponseDTO();
        responseDTO.setId(id);
        responseDTO.setNome(nome);
        responseDTO.setDataCriacao(OffsetDateTime.now());
        responseDTO.setDataAtualizacao(OffsetDateTime.now());
        return responseDTO;
    }
}
