package br.com.fiap.techchallengeapipedidoproduto.produto.presentation.rest.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.controller.ProdutoController;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.ProdutoRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.ProdutoResponseDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProdutoRestControllerImplTest {

    private ProdutoController produtoController;
    private ProdutoRestControllerImpl restController;

    @BeforeEach
    public void setUp() {
        produtoController = Mockito.mock(ProdutoController.class);
        restController = new ProdutoRestControllerImpl(produtoController);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(produtoController);
    }

    @Test
    public void deveBuscarTodosOsProdutosRetornandoResponseOk() {
        // given
        List<ProdutoResponseDTO> produtosEsperados = Arrays.asList(
                criarProdutoResponseDTO("1", "X-Burguer", "Hamburguer com queijo", new BigDecimal("25.90"), "1", "Lanches"),
                criarProdutoResponseDTO("2", "Refrigerante", "Refrigerante 350ml", new BigDecimal("7.50"), "2", "Bebidas")
        );

        when(produtoController.buscarProdutos()).thenReturn(produtosEsperados);

        // when
        ResponseEntity<List<ProdutoResponseDTO>> response = restController.buscarProdutos();

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtosEsperados, response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("X-Burguer", response.getBody().get(0).getNome());
        assertEquals("Refrigerante", response.getBody().get(1).getNome());
        verify(produtoController).buscarProdutos();
    }

    @Test
    public void deveBuscarProdutosPorCategoriaRetornandoResponseOk() {
        // given
        String idCategoria = "1";
        List<ProdutoResponseDTO> produtosEsperados = Arrays.asList(
                criarProdutoResponseDTO("1", "X-Burguer", "Hamburguer com queijo", new BigDecimal("25.90"), idCategoria, "Lanches"),
                criarProdutoResponseDTO("3", "X-Salada", "Hamburguer com salada", new BigDecimal("27.90"), idCategoria, "Lanches")
        );

        when(produtoController.buscarProdutosPorCategoria(idCategoria)).thenReturn(produtosEsperados);

        // when
        ResponseEntity<List<ProdutoResponseDTO>> response = restController.buscarProdutosPorCategoria(idCategoria);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtosEsperados, response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("X-Burguer", response.getBody().get(0).getNome());
        assertEquals("X-Salada", response.getBody().get(1).getNome());
        assertEquals(idCategoria, response.getBody().get(0).getCategoriaProduto().getId());
        assertEquals("Lanches", response.getBody().get(0).getCategoriaProduto().getNome());
        verify(produtoController).buscarProdutosPorCategoria(idCategoria);
    }

    @Test
    public void deveBuscarProdutoPorIdRetornandoResponseOk() {
        // given
        String id = "1";
        ProdutoResponseDTO produtoEsperado = criarProdutoResponseDTO(id, "X-Burguer", "Hamburguer com queijo", 
                new BigDecimal("25.90"), "1", "Lanches");

        when(produtoController.buscarProdutoPorId(id)).thenReturn(produtoEsperado);

        // when
        ResponseEntity<ProdutoResponseDTO> response = restController.buscarProdutoPorId(id);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoEsperado, response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals("X-Burguer", response.getBody().getNome());
        assertEquals("Hamburguer com queijo", response.getBody().getDescricao());
        assertEquals(new BigDecimal("25.90"), response.getBody().getPreco());
        verify(produtoController).buscarProdutoPorId(id);
    }

    @Test
    public void deveCriarProdutoRetornandoCreated() throws URISyntaxException {
        // given
        String id = UUID.randomUUID().toString();
        ProdutoRequestDTO produtoRequest = new ProdutoRequestDTO();
        produtoRequest.setNome("X-Burguer");
        produtoRequest.setDescricao("Hamburguer com queijo");
        produtoRequest.setPreco(new BigDecimal("25.90"));
        produtoRequest.setIdCategoria("1");

        ProdutoResponseDTO produtoResponse = criarProdutoResponseDTO(id, "X-Burguer", "Hamburguer com queijo", 
                new BigDecimal("25.90"), "1", "Lanches");

        when(produtoController.criarProduto(produtoRequest)).thenReturn(produtoResponse);

        // when
        ResponseEntity<ProdutoResponseDTO> response = restController.criarProduto(produtoRequest);

        // then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(produtoResponse, response.getBody());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals("/produtos/" + id, response.getHeaders().getLocation().getPath());
        verify(produtoController).criarProduto(produtoRequest);
    }

    @Test
    public void deveAtualizarProdutoRetornandoOk() {
        // given
        String id = "1";
        ProdutoRequestDTO produtoRequest = new ProdutoRequestDTO();
        produtoRequest.setNome("X-Burguer Especial");
        produtoRequest.setDescricao("Hamburguer com queijo especial");
        produtoRequest.setPreco(new BigDecimal("29.90"));
        produtoRequest.setIdCategoria("1");

        ProdutoResponseDTO produtoResponse = criarProdutoResponseDTO(id, "X-Burguer Especial", "Hamburguer com queijo especial", 
                new BigDecimal("29.90"), "1", "Lanches");

        when(produtoController.atualizarProduto(produtoRequest, id)).thenReturn(produtoResponse);

        // when
        ResponseEntity<ProdutoResponseDTO> response = restController.atualizarProduto(produtoRequest, id);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoResponse, response.getBody());
        assertEquals(id, response.getBody().getId());
        assertEquals("X-Burguer Especial", response.getBody().getNome());
        assertEquals("Hamburguer com queijo especial", response.getBody().getDescricao());
        assertEquals(new BigDecimal("29.90"), response.getBody().getPreco());
        verify(produtoController).atualizarProduto(produtoRequest, id);
    }

    @Test
    public void deveExcluirProdutoRetornandoOk() {
        // given
        String idProduto = "1";
        doNothing().when(produtoController).excluirProduto(idProduto);

        // when
        ResponseEntity<Void> response = restController.excluirProduto(idProduto);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(produtoController).excluirProduto(idProduto);
    }

    private ProdutoResponseDTO criarProdutoResponseDTO(String id, String nome, String descricao, BigDecimal preco, String idCategoria, String nomeCategoria) {
        ProdutoResponseDTO responseDTO = new ProdutoResponseDTO();
        responseDTO.setId(id);
        responseDTO.setNome(nome);
        responseDTO.setDescricao(descricao);
        responseDTO.setPreco(preco);

        if (idCategoria != null) {
            CategoriaProduto categoria = new CategoriaProduto();
            categoria.setId(idCategoria);
            if (nomeCategoria != null) {
                categoria.setNome(nomeCategoria);
            }
            responseDTO.setCategoriaProduto(categoria);
        }

        return responseDTO;
    }
}
