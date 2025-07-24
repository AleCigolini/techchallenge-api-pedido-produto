package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.CriarPedidoMercadoPagoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway.PedidoGateway;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.SalvarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.CriarPedidoMercadoPagoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.exception.PedidoNaoEncontradoException;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.ProdutoPedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.BuscarProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class SalvarPedidoUseCaseImpl implements SalvarPedidoUseCase {

    private final PedidoGateway pedidoGateway;
    private final BuscarProdutoUseCase buscarProdutoUseCase;
    private final CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase;

    public SalvarPedidoUseCaseImpl(PedidoGateway pedidoGateway,
                                   BuscarProdutoUseCase buscarProdutoUseCase,
                                   CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase
    ) {
        this.pedidoGateway = pedidoGateway;
        this.buscarProdutoUseCase = buscarProdutoUseCase;
        this.criarPedidoMercadoPagoUseCase = criarPedidoMercadoPagoUseCase;
    }

    @Override
    @Transactional
    public Pedido criarPedido(Pedido pedido, String idCliente) {
        montarPedido(pedido, idCliente);
        Pedido pedidoCriado = pedidoGateway.criarPedido(pedido);

        var criarPedidoMercadoPagoRequestDto = CriarPedidoMercadoPagoRequestDto.builder()
                .codigoPedido(pedidoCriado.getId())
                .codigo(pedidoCriado.getCodigo())
                .preco(pedidoCriado.getPreco())
                .codigoCliente(pedidoCriado.getIdCliente())
                .produtos(pedidoCriado.getProdutos())
                .build();

        criarPedidoMercadoPagoUseCase.criarPedidoMercadoPago(criarPedidoMercadoPagoRequestDto);

        return pedidoCriado;
    }

    @Override
    @Transactional
    public Pedido atualizarPedido(Pedido pedido) {
        Pedido pedidoEncontrado = pedidoGateway.buscarPedidoPorId(pedido.getId());

        if (pedidoEncontrado == null) {
            throw new PedidoNaoEncontradoException(pedido.getId());
        }

        pedidoEncontrado.setStatus(pedido.getStatus());
        pedidoEncontrado.setIdPagamento(pedido.getIdPagamento());
        return pedidoGateway.salvarPedido(pedidoEncontrado);
    }

    @Transactional
    public Pedido atualizarPedidoRecebido(String idPedido, String idPagamento) {
        Pedido pedido = new Pedido();
        pedido.setId(idPedido);
        pedido.setStatus(StatusPedidoEnum.RECEBIDO.toString());
        pedido.setIdPagamento(UUID.fromString(idPagamento));
        return atualizarPedido(pedido);
    }

    public void montarPedido(Pedido pedido, String idCliente) {
        pedido.setIdCliente(UUID.fromString(idCliente));

        List<ProdutoPedido> produtos = new ArrayList<>();
        ProdutoPedido produtoPedido;
        var precoTotal = new BigDecimal(BigInteger.ZERO);

        for (ProdutoPedido produtoPedidoAux : pedido.getProdutos()) {
            Produto produto = buscarProdutoUseCase.buscarProdutoPorId(produtoPedidoAux.getProduto().getId());

            precoTotal = precoTotal.add(produto.getPreco().multiply(BigDecimal.valueOf(produtoPedidoAux.getQuantidade())));

            produtoPedido = new ProdutoPedido();
            produtoPedido.setProduto(produto);
            produtoPedido.setQuantidade(produtoPedidoAux.getQuantidade());
            produtoPedido.setObservacao(produtoPedidoAux.getObservacao());
            produtos.add(produtoPedido);
        }
        pedido.setProdutos(produtos);
        pedido.setCodigo(gerarCodigo());
        pedido.setStatus(StatusPedidoEnum.ABERTO.toString());
        pedido.setPreco(precoTotal);
    }

    private String gerarCodigo() {
        var CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        var TAMANHO_CODIGO = 5;

        Random random = new Random();
        StringBuilder codigo = new StringBuilder();

        for (int i = 0; i < TAMANHO_CODIGO; i++) {
            int indice = random.nextInt(CARACTERES.length());
            codigo.append(CARACTERES.charAt(indice));
        }
        return codigo.toString();
    }
}