package br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.ProdutoGateway;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.SalvarProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SalvarProdutoUseCaseImpl implements SalvarProdutoUseCase {

    private final ProdutoGateway produtoGateway;

    public SalvarProdutoUseCaseImpl(ProdutoGateway produtoGateway) {
        this.produtoGateway = produtoGateway;
    }

    @Override
    public Produto criarProduto(Produto produto) {
        return produtoGateway.criarProduto(produto);
    }
}
