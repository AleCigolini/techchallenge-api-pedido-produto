package br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.client.mercadopago.mapper.impl;

import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.ProdutoPedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.client.mercadopago.request.MercadoPagoOrderItemRequest;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.client.mercadopago.request.MercadoPagoOrderRequest;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MercadoPagoOrderRequestMapperImplTest {

    private MercadoPagoOrderRequestMapperImpl mapper;

    @BeforeEach
    public void setUp() {
        mapper = new MercadoPagoOrderRequestMapperImpl();
    }

    @Test
    public void deveMapearPedidoParaMercadoPagoOrderRequest() {
        // given
        String idPedido = UUID.randomUUID().toString();
        String codigoPedido = "ABC123";
        String observacaoPedido = "Observação do pedido de teste";
        BigDecimal precoPedido = new BigDecimal("66.80");

        Pedido pedido = criarPedidoCompleto(idPedido, codigoPedido, observacaoPedido, precoPedido);

        // when
        MercadoPagoOrderRequest resultado = mapper.pedidoParaMercadoPagoOrderItemRequest(pedido);

        // then
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getExternalReference());
        assertEquals(codigoPedido, resultado.getTitle());
        assertEquals(observacaoPedido, resultado.getDescription());
        assertEquals(precoPedido, resultado.getTotalAmount());

        // Verificar items
        List<MercadoPagoOrderItemRequest> items = resultado.getItems();
        assertEquals(2, items.size());

        // Verificar primeiro item
        MercadoPagoOrderItemRequest primeiroItem = items.get(0);
        assertEquals("1", primeiroItem.getSkuNumber());
        assertEquals("Lanche", primeiroItem.getCategory());
        assertEquals("X-Burguer", primeiroItem.getTitle());
        assertEquals("Hamburguer com queijo", primeiroItem.getDescription());
        assertEquals(2L, primeiroItem.getQuantity());
        assertEquals(new BigDecimal("25.90"), primeiroItem.getUnitPrice());
        assertEquals("unit", primeiroItem.getUnitMeasure());
        assertEquals(new BigDecimal("51.80"), primeiroItem.getTotalAmount());

        // Verificar segundo item
        MercadoPagoOrderItemRequest segundoItem = items.get(1);
        assertEquals("2", segundoItem.getSkuNumber());
        assertEquals("Bebida", segundoItem.getCategory());
        assertEquals("Refrigerante", segundoItem.getTitle());
        assertEquals("Refrigerante 350ml", segundoItem.getDescription());
        assertEquals(2L, segundoItem.getQuantity());
        assertEquals(new BigDecimal("7.50"), segundoItem.getUnitPrice());
        assertEquals("unit", segundoItem.getUnitMeasure());
        assertEquals(new BigDecimal("15.00"), segundoItem.getTotalAmount());
    }

    @Test
    public void deveMapearPedidoComUmProdutoParaMercadoPagoOrderRequest() {
        // given
        String idPedido = UUID.randomUUID().toString();
        String codigoPedido = "XYZ789";
        String observacaoPedido = "Pedido com um produto";
        BigDecimal precoPedido = new BigDecimal("25.90");

        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setCodigo(codigoPedido);
        pedido.setObservacao(observacaoPedido);
        pedido.setStatus(StatusPedidoEnum.ABERTO.toString());
        pedido.setPreco(precoPedido);

        // Configurar produto único
        Produto produto = new Produto();
        produto.setId("1");
        produto.setNome("X-Burguer");
        produto.setDescricao("Hamburguer com queijo");
        produto.setPreco(new BigDecimal("25.90"));

        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setNome("Lanche");
        produto.setCategoria(categoria);

        ProdutoPedido produtoPedido = new ProdutoPedido();
        produtoPedido.setProduto(produto);
        produtoPedido.setQuantidade(1L);
        produtoPedido.setObservacao("Sem cebola");

        pedido.setProdutos(Arrays.asList(produtoPedido));

        // when
        MercadoPagoOrderRequest resultado = mapper.pedidoParaMercadoPagoOrderItemRequest(pedido);

        // then
        assertNotNull(resultado);
        assertEquals(idPedido, resultado.getExternalReference());
        assertEquals(codigoPedido, resultado.getTitle());
        assertEquals(observacaoPedido, resultado.getDescription());
        assertEquals(precoPedido, resultado.getTotalAmount());

        // Verificar item único
        List<MercadoPagoOrderItemRequest> items = resultado.getItems();
        assertEquals(1, items.size());

        MercadoPagoOrderItemRequest item = items.get(0);
        assertEquals("1", item.getSkuNumber());
        assertEquals("Lanche", item.getCategory());
        assertEquals("X-Burguer", item.getTitle());
        assertEquals("Hamburguer com queijo", item.getDescription());
        assertEquals(1L, item.getQuantity());
        assertEquals(new BigDecimal("25.90"), item.getUnitPrice());
        assertEquals("unit", item.getUnitMeasure());
        assertEquals(new BigDecimal("25.90"), item.getTotalAmount());
    }

    private Pedido criarPedidoCompleto(String id, String codigo, String observacao, BigDecimal preco) {
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setCodigo(codigo);
        pedido.setObservacao(observacao);
        pedido.setStatus(StatusPedidoEnum.ABERTO.toString());
        pedido.setPreco(preco);

        // Primeiro produto
        Produto produto1 = new Produto();
        produto1.setId("1");
        produto1.setNome("X-Burguer");
        produto1.setDescricao("Hamburguer com queijo");
        produto1.setPreco(new BigDecimal("25.90"));

        CategoriaProduto categoria1 = new CategoriaProduto();
        categoria1.setNome("Lanche");
        produto1.setCategoria(categoria1);

        ProdutoPedido produtoPedido1 = new ProdutoPedido();
        produtoPedido1.setProduto(produto1);
        produtoPedido1.setQuantidade(2L);
        produtoPedido1.setObservacao("Sem cebola");

        // Segundo produto
        Produto produto2 = new Produto();
        produto2.setId("2");
        produto2.setNome("Refrigerante");
        produto2.setDescricao("Refrigerante 350ml");
        produto2.setPreco(new BigDecimal("7.50"));

        CategoriaProduto categoria2 = new CategoriaProduto();
        categoria2.setNome("Bebida");
        produto2.setCategoria(categoria2);

        ProdutoPedido produtoPedido2 = new ProdutoPedido();
        produtoPedido2.setProduto(produto2);
        produtoPedido2.setQuantidade(2L);

        pedido.setProdutos(Arrays.asList(produtoPedido1, produtoPedido2));

        return pedido;
    }
}
