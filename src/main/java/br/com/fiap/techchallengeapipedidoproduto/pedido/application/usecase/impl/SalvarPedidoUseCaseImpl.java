package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.cliente.application.usecase.ConsultarClienteUseCase;
import br.com.fiap.techchallengeapipedidoproduto.cliente.domain.Cliente;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.SalvarPagamentoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway.PedidoGateway;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.CriarPedidoMercadoPagoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.SalvarPedidoUseCase;
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

public class SalvarPedidoUseCaseImpl implements SalvarPedidoUseCase {

    private final PedidoGateway pedidoGateway;
    private final ConsultarClienteUseCase consultarClienteUseCase;
    private final BuscarProdutoUseCase buscarProdutoUseCase;
    private final SalvarPagamentoUseCase salvarPagamentoUseCase;
    private final CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase;

    public SalvarPedidoUseCaseImpl(PedidoGateway pedidoGateway,
                                   BuscarProdutoUseCase buscarProdutoUseCase,
                                   ConsultarClienteUseCase consultarClienteUseCase,
                                   SalvarPagamentoUseCase salvarPagamentoUseCase,
                                   CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase
    ) {
        this.pedidoGateway = pedidoGateway;
        this.buscarProdutoUseCase = buscarProdutoUseCase;
        this.consultarClienteUseCase = consultarClienteUseCase;
        this.salvarPagamentoUseCase = salvarPagamentoUseCase;
        this.criarPedidoMercadoPagoUseCase = criarPedidoMercadoPagoUseCase;
    }

    @Override
    @Transactional
    public Pedido criarPedido(Pedido pedido) {

        montarPedido(pedido);
        Pedido pedidoCriado = pedidoGateway.criarPedido(pedido);

        boolean criouPedidoMercadoPago = criarPedidoMercadoPagoUseCase.criarPedidoMercadoPago(pedidoCriado);

        salvarPagamentoUseCase.criarPagamentoPendenteParaOPedido(pedidoCriado, criouPedidoMercadoPago);

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
        pedidoEncontrado.setCodigoPagamento(pedido.getCodigoPagamento());
        return pedidoGateway.salvarPedido(pedidoEncontrado);
    }

    @Override
    @Transactional
    public Pedido atualizarStatusPedido(StatusPedidoEnum statusPedidoEnum, String id) {
        Pedido pedido = new Pedido();
        pedido.setId(id);
        pedido.setStatus(statusPedidoEnum.toString());
        return atualizarPedido(pedido);
    }

    public void montarPedido(Pedido pedido) {

        if (pedido.getCliente() != null) {
            Cliente cliente = consultarClienteUseCase.buscarClientePorCpf(pedido.getCliente().getCpf());
            pedido.setCliente(cliente);
        }

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