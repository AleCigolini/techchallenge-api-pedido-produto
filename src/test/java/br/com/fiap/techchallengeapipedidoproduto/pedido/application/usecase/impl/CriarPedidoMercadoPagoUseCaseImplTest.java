package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.impl.CriarPedidoMercadoPagoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.infrastructure.client.PagamentoClient;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;

public class CriarPedidoMercadoPagoUseCaseImplTest {

    private PagamentoClient pagamentoClient;
    private CriarPedidoMercadoPagoUseCaseImpl criarPedidoMercadoPagoUseCase;

    @BeforeEach
    public void setUp() {
        pagamentoClient = Mockito.mock(PagamentoClient.class);
        criarPedidoMercadoPagoUseCase = new CriarPedidoMercadoPagoUseCaseImpl(pagamentoClient);

        doNothing().when(pagamentoClient).criarPedidoMercadoPago(any());
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(pagamentoClient);
    }

    @Test
    public void deveCriarPedidoMercadoPagoComSucesso() {
        // given
        Pedido pedido = new Pedido();

        // when
        criarPedidoMercadoPagoUseCase.criarPedidoMercadoPago(pedido);

        // then
    }
}

