package br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.ProdutoGateway;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.AtualizarProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AtualizarProdutoUseCaseImpl implements AtualizarProdutoUseCase {

    private final ProdutoGateway produtoGateway;

    public AtualizarProdutoUseCaseImpl(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    @Override
    public Produto atualizarProduto(Produto produto) {
        return produtoGateway.atualizarProduto(produto);
    }
}

