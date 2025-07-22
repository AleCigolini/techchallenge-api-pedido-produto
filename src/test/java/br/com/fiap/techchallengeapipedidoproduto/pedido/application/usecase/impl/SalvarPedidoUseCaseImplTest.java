package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.cliente.application.usecase.ConsultarClienteUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.SalvarPagamentoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway.PedidoGateway;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.CriarPedidoMercadoPagoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.exception.PedidoNaoEncontradoException;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.ProdutoPedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.BuscarProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SalvarPedidoUseCaseImplTest {

    private PedidoGateway pedidoGateway;
    private BuscarProdutoUseCase buscarProdutoUseCase;
    private CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase;
    private SalvarPedidoUseCaseImpl salvarPedidoUseCase;

    @BeforeEach
    public void setUp() {
        pedidoGateway = Mockito.mock(PedidoGateway.class);
        buscarProdutoUseCase = Mockito.mock(BuscarProdutoUseCase.class);
        criarPedidoMercadoPagoUseCase = Mockito.mock(CriarPedidoMercadoPagoUseCase.class);
        ConsultarClienteUseCase consultarClienteUseCase = Mockito.mock(ConsultarClienteUseCase.class);
        SalvarPagamentoUseCase salvarPagamentoUseCase = Mockito.mock(SalvarPagamentoUseCase.class);

        salvarPedidoUseCase = new SalvarPedidoUseCaseImpl(
                pedidoGateway,
                buscarProdutoUseCase,
                consultarClienteUseCase,
                salvarPagamentoUseCase,
                criarPedidoMercadoPagoUseCase
        );
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset();
    }

    @Test
    public void deveCriarPedidoComSucesso() {
        // given
        Pedido pedido = new Pedido();

        // Criar lista de produtos para o pedido
        List<ProdutoPedido> produtos = new ArrayList<>();
        ProdutoPedido produtoPedido = new ProdutoPedido();
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("X-Burguer");
        produto.setPreco(new BigDecimal("25.90"));
        produtoPedido.setProduto(produto);
        produtoPedido.setQuantidade(Long.valueOf("2"));
        produtoPedido.setObservacao("Sem cebola");
        produtos.add(produtoPedido);
        pedido.setProdutos(produtos);

        // Mock das respostas
        when(buscarProdutoUseCase.buscarProdutoPorId("1")).thenReturn(produto);
        when(pedidoGateway.criarPedido(any(Pedido.class))).thenAnswer(invocation -> {
            Pedido p = invocation.getArgument(0);
            p.setId("pedido-123");
            return p;
        });
        doNothing().when(criarPedidoMercadoPagoUseCase).criarPedidoMercadoPago(any(Pedido.class));

        // when
        Pedido resultado = salvarPedidoUseCase.criarPedido(pedido);

        // then
        assertNotNull(resultado);
        assertEquals("pedido-123", resultado.getId());
        assertEquals(StatusPedidoEnum.ABERTO.toString(), resultado.getStatus());
        assertNotNull(resultado.getCodigo());
        assertEquals(new BigDecimal("51.80"), resultado.getPreco());

        // Verificar chamadas aos mocks
        verify(buscarProdutoUseCase).buscarProdutoPorId("1");
        verify(pedidoGateway).criarPedido(any(Pedido.class));
        verify(criarPedidoMercadoPagoUseCase).criarPedidoMercadoPago(any(Pedido.class));
    }

    @Test
    public void deveAtualizarPedidoComSucesso() {
        // given
        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setId("pedido-123");
        pedidoExistente.setCodigo("ABC123");
        pedidoExistente.setStatus(StatusPedidoEnum.ABERTO.toString());
        pedidoExistente.setPreco(new BigDecimal("51.80"));

        Pedido pedidoAtualizado = new Pedido();
        pedidoAtualizado.setId("pedido-123");
        pedidoAtualizado.setStatus(StatusPedidoEnum.RECEBIDO.toString());
        pedidoAtualizado.setCodigoPagamento("payment-789");

        when(pedidoGateway.buscarPedidoPorId("pedido-123")).thenReturn(pedidoExistente);
        when(pedidoGateway.salvarPedido(any(Pedido.class))).thenReturn(pedidoExistente);

        // when
        Pedido resultado = salvarPedidoUseCase.atualizarPedido(pedidoAtualizado);

        // then
        assertNotNull(resultado);
        assertEquals("pedido-123", resultado.getId());
        assertEquals(StatusPedidoEnum.RECEBIDO.toString(), resultado.getStatus());
        assertEquals("payment-789", resultado.getCodigoPagamento());

        // Capturar o argumento passado para salvarPedido para verificações adicionais
        ArgumentCaptor<Pedido> pedidoCaptor = ArgumentCaptor.forClass(Pedido.class);
        verify(pedidoGateway).salvarPedido(pedidoCaptor.capture());
        Pedido pedidoSalvo = pedidoCaptor.getValue();

        assertEquals("pedido-123", pedidoSalvo.getId());
        assertEquals(StatusPedidoEnum.RECEBIDO.toString(), pedidoSalvo.getStatus());
        assertEquals("payment-789", pedidoSalvo.getCodigoPagamento());
        // Os outros campos devem permanecer inalterados
        assertEquals("ABC123", pedidoSalvo.getCodigo());
        assertEquals(new BigDecimal("51.80"), pedidoSalvo.getPreco());
    }

    @Test
    public void deveLancarExcecaoQuandoPedidoNaoExistente() {
        // given
        Pedido pedidoAtualizar = new Pedido();
        pedidoAtualizar.setId("pedido-nao-existente");
        pedidoAtualizar.setStatus(StatusPedidoEnum.RECEBIDO.toString());

        when(pedidoGateway.buscarPedidoPorId("pedido-nao-existente")).thenReturn(null);

        // when/then
        assertThrows(PedidoNaoEncontradoException.class, () -> salvarPedidoUseCase.atualizarPedido(pedidoAtualizar));
    }

    @Test
    public void deveAtualizarStatusPedidoComSucesso() {
        // given
        String idPedido = "pedido-123";
        StatusPedidoEnum novoStatus = StatusPedidoEnum.FINALIZADO;

        Pedido pedidoExistente = new Pedido();
        pedidoExistente.setId(idPedido);
        pedidoExistente.setCodigo("ABC123");
        pedidoExistente.setStatus(StatusPedidoEnum.EM_PREPARACAO.toString());

        when(pedidoGateway.buscarPedidoPorId(idPedido)).thenReturn(pedidoExistente);
        when(pedidoGateway.salvarPedido(any(Pedido.class))).thenReturn(pedidoExistente);

        // when
        Pedido resultado = salvarPedidoUseCase.atualizarStatusPedido(novoStatus, idPedido);

        // then
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getId());
        assertEquals(novoStatus.toString(), resultado.getStatus());

        // Verificar chamadas aos mocks
        verify(pedidoGateway).buscarPedidoPorId(idPedido);
        verify(pedidoGateway).salvarPedido(any(Pedido.class));
    }

    @Test
    public void deveMontarPedidoCorretamente() {
        // given
        Pedido pedido = new Pedido();

        // Criar lista de produtos para o pedido
        List<ProdutoPedido> produtos = new ArrayList<>();

        // Produto 1
        ProdutoPedido produtoPedido1 = new ProdutoPedido();
        Produto produto1 = new Produto();
        produto1.setId("1");
        produtoPedido1.setProduto(produto1);
        produtoPedido1.setQuantidade(Long.valueOf("2"));
        produtoPedido1.setObservacao("Sem cebola");
        produtos.add(produtoPedido1);

        // Produto 2
        ProdutoPedido produtoPedido2 = new ProdutoPedido();
        Produto produto2 = new Produto();
        produto2.setId("2");
        produtoPedido2.setProduto(produto2);
        produtoPedido2.setQuantidade(Long.valueOf("2"));
        produtoPedido2.setObservacao("Com extra de queijo");
        produtos.add(produtoPedido2);

        pedido.setProdutos(produtos);

        // Mock das respostas para busca de produtos
        Produto produtoCompleto1 = new Produto();
        produtoCompleto1.setId("1");
        produtoCompleto1.setNome("X-Burguer");
        produtoCompleto1.setPreco(new BigDecimal("25.90"));

        Produto produtoCompleto2 = new Produto();
        produtoCompleto2.setId("2");
        produtoCompleto2.setNome("Refrigerante");
        produtoCompleto2.setPreco(new BigDecimal("7.50"));

        when(buscarProdutoUseCase.buscarProdutoPorId("1")).thenReturn(produtoCompleto1);
        when(buscarProdutoUseCase.buscarProdutoPorId("2")).thenReturn(produtoCompleto2);

        // when
        salvarPedidoUseCase.montarPedido(pedido);

        // then
        assertNotNull(pedido.getCodigo());
        assertEquals(StatusPedidoEnum.ABERTO.toString(), pedido.getStatus());
        assertEquals(new BigDecimal("66.80"), pedido.getPreco());
        assertEquals(2, pedido.getProdutos().size());

        // Verificar chamadas aos mocks
        verify(buscarProdutoUseCase, times(1)).buscarProdutoPorId("1");
        verify(buscarProdutoUseCase, times(1)).buscarProdutoPorId("2");
    }
}
