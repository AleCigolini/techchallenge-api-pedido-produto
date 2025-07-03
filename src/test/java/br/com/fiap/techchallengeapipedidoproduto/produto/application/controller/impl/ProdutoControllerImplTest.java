package br.com.fiap.techchallengeapipedidoproduto.produto.application.controller.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.controller.CategoriaProdutoController;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.ProdutoMapper;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.presenter.ProdutoPresenter;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.AtualizarProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.BuscarProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.ExcluirProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.SalvarProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.ProdutoRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.ProdutoResponseDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adpater.ProdutoDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProdutoControllerImplTest {

    private ProdutoControllerImpl controller;
    private ModelMapper modelMapper;
    private ProdutoDatabase produtoDatabase;
    private CategoriaProdutoController categoriaProdutoController;
    private BuscarProdutoUseCase buscarProdutoUseCase;
    private SalvarProdutoUseCase salvarProdutoUseCase;
    private AtualizarProdutoUseCase atualizarProdutoUseCase;
    private ExcluirProdutoUseCase excluirProdutoUseCase;
    private ProdutoPresenter produtoPresenter;
    private ProdutoMapper produtoMapper;

    @BeforeEach
    public void setUp() {
        modelMapper = Mockito.mock(ModelMapper.class);
        produtoDatabase = Mockito.mock(ProdutoDatabase.class);
        categoriaProdutoController = Mockito.mock(CategoriaProdutoController.class);
        buscarProdutoUseCase = Mockito.mock(BuscarProdutoUseCase.class);
        salvarProdutoUseCase = Mockito.mock(SalvarProdutoUseCase.class);
        atualizarProdutoUseCase = Mockito.mock(AtualizarProdutoUseCase.class);
        excluirProdutoUseCase = Mockito.mock(ExcluirProdutoUseCase.class);
        produtoPresenter = Mockito.mock(ProdutoPresenter.class);
        produtoMapper = Mockito.mock(ProdutoMapper.class);

        controller = new ProdutoControllerImpl(modelMapper, produtoDatabase, categoriaProdutoController);

        ReflectionTestUtils.setField(controller, "buscarProdutoUseCase", buscarProdutoUseCase);
        ReflectionTestUtils.setField(controller, "salvarProdutoUseCase", salvarProdutoUseCase);
        ReflectionTestUtils.setField(controller, "atualizarProdutoUseCase", atualizarProdutoUseCase);
        ReflectionTestUtils.setField(controller, "excluirProdutoUseCase", excluirProdutoUseCase);
        ReflectionTestUtils.setField(controller, "produtoPresenter", produtoPresenter);
        ReflectionTestUtils.setField(controller, "produtoMapper", produtoMapper);
    }

    @Test
    public void deveBuscarTodosOsProdutos() {
        // given
        List<Produto> produtos = Arrays.asList(
                criarProduto("1", "X-Burguer", "Hamburguer com queijo", new BigDecimal("25.90"), "1"),
                criarProduto("2", "Refrigerante", "Refrigerante 350ml", new BigDecimal("7.50"), "2")
        );

        List<ProdutoResponseDTO> produtosResponse = Arrays.asList(
                criarProdutoResponseDTO("1", "X-Burguer", "Hamburguer com queijo", new BigDecimal("25.90"), "1", "Lanche"),
                criarProdutoResponseDTO("2", "Refrigerante", "Refrigerante 350ml", new BigDecimal("7.50"), "2", "Bebida")
        );

        when(buscarProdutoUseCase.buscarProdutos()).thenReturn(produtos);
        when(produtoPresenter.produtosParaProdutosResponseDTO(produtos)).thenReturn(produtosResponse);

        // when
        List<ProdutoResponseDTO> resultado = controller.buscarProdutos();

        // then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("X-Burguer", resultado.get(0).getNome());
        assertEquals("Refrigerante", resultado.get(1).getNome());
        verify(buscarProdutoUseCase, times(1)).buscarProdutos();
        verify(produtoPresenter, times(1)).produtosParaProdutosResponseDTO(produtos);
    }

    @Test
    public void deveBuscarProdutosPorCategoria() {
        // given
        String idCategoria = "1";
        String nomeCategoria = "Lanche";

        List<Produto> produtos = Arrays.asList(
                criarProduto("1", "X-Burguer", "Hamburguer com queijo", new BigDecimal("25.90"), idCategoria),
                criarProduto("3", "X-Salada", "Hamburguer com salada", new BigDecimal("27.90"), idCategoria)
        );

        List<ProdutoResponseDTO> produtosResponse = Arrays.asList(
                criarProdutoResponseDTO("1", "X-Burguer", "Hamburguer com queijo", new BigDecimal("25.90"), idCategoria, null),
                criarProdutoResponseDTO("3", "X-Salada", "Hamburguer com salada", new BigDecimal("27.90"), idCategoria, null)
        );

        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setId(idCategoria);
        categoria.setNome(nomeCategoria);

        when(buscarProdutoUseCase.buscarProdutosPorCategoria(idCategoria)).thenReturn(produtos);
        when(produtoPresenter.produtosParaProdutosResponseDTO(produtos)).thenReturn(produtosResponse);
        when(categoriaProdutoController.buscarCategoriaProdutoPorId(idCategoria)).thenReturn(categoria);

        // when
        List<ProdutoResponseDTO> resultado = controller.buscarProdutosPorCategoria(idCategoria);

        // then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("X-Burguer", resultado.get(0).getNome());
        assertEquals("X-Salada", resultado.get(1).getNome());
        assertEquals(idCategoria, resultado.get(0).getCategoriaProduto().getId());
        assertEquals(nomeCategoria, resultado.get(0).getCategoriaProduto().getNome());
        assertEquals(idCategoria, resultado.get(1).getCategoriaProduto().getId());
        assertEquals(nomeCategoria, resultado.get(1).getCategoriaProduto().getNome());
        verify(buscarProdutoUseCase, times(1)).buscarProdutosPorCategoria(idCategoria);
        verify(produtoPresenter, times(1)).produtosParaProdutosResponseDTO(produtos);
        verify(categoriaProdutoController, times(2)).buscarCategoriaProdutoPorId(idCategoria);
    }

    @Test
    public void deveBuscarProdutoPorId() {
        // given
        String id = "1";
        Produto produto = criarProduto(id, "X-Burguer", "Hamburguer com queijo", new BigDecimal("25.90"), "1");
        ProdutoResponseDTO produtoResponse = criarProdutoResponseDTO(id, "X-Burguer", "Hamburguer com queijo", new BigDecimal("25.90"), "1", "Lanche");

        when(buscarProdutoUseCase.buscarProdutoPorId(id)).thenReturn(produto);
        when(produtoPresenter.produtoParaProdutoResponseDTO(produto)).thenReturn(produtoResponse);

        // when
        ProdutoResponseDTO resultado = controller.buscarProdutoPorId(id);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("X-Burguer", resultado.getNome());
        assertEquals("Hamburguer com queijo", resultado.getDescricao());
        verify(buscarProdutoUseCase, times(1)).buscarProdutoPorId(id);
        verify(produtoPresenter, times(1)).produtoParaProdutoResponseDTO(produto);
    }

    @Test
    public void deveCriarProduto() {
        // given
        String id = UUID.randomUUID().toString();
        String idCategoria = "1";
        String nomeCategoria = "Lanche";

        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome("X-Burguer");
        requestDTO.setDescricao("Hamburguer com queijo");
        requestDTO.setPreco(new BigDecimal("25.90"));
        requestDTO.setIdCategoria(idCategoria);

        Produto produto = criarProduto(null, "X-Burguer", "Hamburguer com queijo", new BigDecimal("25.90"), idCategoria);
        Produto produtoCriado = criarProduto(id, "X-Burguer", "Hamburguer com queijo", new BigDecimal("25.90"), idCategoria);
        ProdutoResponseDTO produtoResponse = criarProdutoResponseDTO(id, "X-Burguer", "Hamburguer com queijo", new BigDecimal("25.90"), idCategoria, null);

        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setId(idCategoria);
        categoria.setNome(nomeCategoria);

        when(produtoMapper.produtoRequestDtoParaProduto(requestDTO)).thenReturn(produto);
        when(salvarProdutoUseCase.criarProduto(produto)).thenReturn(produtoCriado);
        when(categoriaProdutoController.buscarCategoriaProdutoPorId(idCategoria)).thenReturn(categoria);
        when(produtoPresenter.produtoParaProdutoResponseDTO(produtoCriado)).thenReturn(produtoResponse);

        // when
        ProdutoResponseDTO resultado = controller.criarProduto(requestDTO);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("X-Burguer", resultado.getNome());
        assertEquals("Hamburguer com queijo", resultado.getDescricao());
        assertEquals(new BigDecimal("25.90"), resultado.getPreco());
        assertNotNull(resultado.getCategoriaProduto());
        assertEquals(idCategoria, resultado.getCategoriaProduto().getId());
        assertEquals(nomeCategoria, resultado.getCategoriaProduto().getNome());
        verify(produtoMapper, times(1)).produtoRequestDtoParaProduto(requestDTO);
        verify(salvarProdutoUseCase, times(1)).criarProduto(produto);
        verify(categoriaProdutoController, times(1)).buscarCategoriaProdutoPorId(idCategoria);
        verify(produtoPresenter, times(1)).produtoParaProdutoResponseDTO(produtoCriado);
    }

    @Test
    public void deveAtualizarProduto() {
        // given
        String id = "1";
        String idCategoria = "2";
        String nomeCategoria = "Bebida";

        ProdutoRequestDTO requestDTO = new ProdutoRequestDTO();
        requestDTO.setNome("Refrigerante Cola");
        requestDTO.setDescricao("Refrigerante Cola 350ml");
        requestDTO.setPreco(new BigDecimal("8.50"));
        requestDTO.setIdCategoria(idCategoria);

        Produto produto = criarProduto(null, "Refrigerante Cola", "Refrigerante Cola 350ml", new BigDecimal("8.50"), idCategoria);
        Produto produtoAtualizado = criarProduto(id, "Refrigerante Cola", "Refrigerante Cola 350ml", new BigDecimal("8.50"), idCategoria);
        ProdutoResponseDTO produtoResponse = criarProdutoResponseDTO(id, "Refrigerante Cola", "Refrigerante Cola 350ml", new BigDecimal("8.50"), idCategoria, null);

        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setId(idCategoria);
        categoria.setNome(nomeCategoria);

        when(buscarProdutoUseCase.buscarProdutoPorId(id)).thenReturn(criarProduto(id, "Refrigerante", "Refrigerante 350ml", new BigDecimal("7.50"), "2"));
        when(produtoMapper.produtoRequestDtoParaProduto(requestDTO)).thenReturn(produto);
        when(atualizarProdutoUseCase.atualizarProduto(any(Produto.class))).thenReturn(produtoAtualizado);
        when(categoriaProdutoController.buscarCategoriaProdutoPorId(idCategoria)).thenReturn(categoria);
        when(produtoPresenter.produtoParaProdutoResponseDTO(produtoAtualizado)).thenReturn(produtoResponse);

        // when
        ProdutoResponseDTO resultado = controller.atualizarProduto(requestDTO, id);

        // then
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        assertEquals("Refrigerante Cola", resultado.getNome());
        assertEquals("Refrigerante Cola 350ml", resultado.getDescricao());
        assertEquals(new BigDecimal("8.50"), resultado.getPreco());
        assertNotNull(resultado.getCategoriaProduto());
        assertEquals(idCategoria, resultado.getCategoriaProduto().getId());
        assertEquals(nomeCategoria, resultado.getCategoriaProduto().getNome());
        verify(buscarProdutoUseCase, times(1)).buscarProdutoPorId(id);
        verify(produtoMapper, times(1)).produtoRequestDtoParaProduto(requestDTO);
        verify(atualizarProdutoUseCase, times(1)).atualizarProduto(any(Produto.class));
        verify(categoriaProdutoController, times(1)).buscarCategoriaProdutoPorId(idCategoria);
        verify(produtoPresenter, times(1)).produtoParaProdutoResponseDTO(produtoAtualizado);
    }

    @Test
    public void deveExcluirProduto() {
        // given
        String id = "1";
        doNothing().when(excluirProdutoUseCase).excluirProduto(id);

        // when
        controller.excluirProduto(id);

        // then
        verify(excluirProdutoUseCase, times(1)).excluirProduto(id);
    }

    private Produto criarProduto(String id, String nome, String descricao, BigDecimal preco, String idCategoria) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNome(nome);
        produto.setDescricao(descricao);
        produto.setPreco(preco);

        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setId(idCategoria);
        produto.setCategoria(categoria);

        return produto;
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
