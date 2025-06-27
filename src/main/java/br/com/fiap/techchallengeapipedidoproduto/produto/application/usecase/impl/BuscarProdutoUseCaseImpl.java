package br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.ProdutoGateway;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.BuscarProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.exception.ProdutoNaoEncontradoException;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class BuscarProdutoUseCaseImpl implements BuscarProdutoUseCase {

    private final ProdutoGateway produtoGateway;

    public BuscarProdutoUseCaseImpl(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produto> buscarProdutos() {
        return produtoGateway.buscarProdutos();
    }

    @Override
    @Transactional(readOnly = true)
    public Produto buscarProdutoPorId(String id) {
        return produtoGateway.buscarProdutoPorId(id).orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Produto> buscarProdutosPorCategoria(String idCategoriaProduto) {
        return produtoGateway.buscarProdutosPorCategoria(idCategoriaProduto);
    }
}

