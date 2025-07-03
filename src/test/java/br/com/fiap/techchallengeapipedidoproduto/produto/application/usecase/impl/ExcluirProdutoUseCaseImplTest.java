package br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.ProdutoGateway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

public class ExcluirProdutoUseCaseImplTest {

    private ProdutoGateway produtoGateway;
    private ExcluirProdutoUseCaseImpl excluirProdutoUseCase;

    @BeforeEach
    public void setUp() {
        produtoGateway = Mockito.mock(ProdutoGateway.class);
        excluirProdutoUseCase = new ExcluirProdutoUseCaseImpl(produtoGateway);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(produtoGateway);
    }

    @Test
    public void deveExcluirProduto() {
        // given
        String idProduto = UUID.randomUUID().toString();
        doNothing().when(produtoGateway).excluirProduto(idProduto);

        // when
        excluirProdutoUseCase.excluirProduto(idProduto);

        // then
        verify(produtoGateway).excluirProduto(idProduto);
    }
}
