package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.impl.CriarPedidoMercadoPagoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.infrastructure.client.PagamentoClient;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.CriarPedidoMercadoPagoRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.any;

public class CriarPedidoMercadoPagoUseCaseImplTest {

    private PagamentoClient pagamentoClient;
    private CriarPedidoMercadoPagoUseCaseImpl criarPedidoMercadoPagoUseCase;

    @BeforeEach
    public void setUp() {
        pagamentoClient = Mockito.mock(PagamentoClient.class);
        criarPedidoMercadoPagoUseCase = new CriarPedidoMercadoPagoUseCaseImpl(pagamentoClient);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(pagamentoClient);
    }

    @Test
    public void deveCriarPedidoMercadoPagoComSucesso() {
        // given
        Mockito.when(pagamentoClient.criarPedido(any())).thenReturn(null);
        CriarPedidoMercadoPagoRequestDto criarPedidoMercadoPagoRequestDto = CriarPedidoMercadoPagoRequestDto.builder().build();

        // when
        criarPedidoMercadoPagoUseCase.criarPedidoMercadoPago(criarPedidoMercadoPagoRequestDto);

        // then
        Mockito.verify(pagamentoClient, Mockito.times(1)).criarPedido(criarPedidoMercadoPagoRequestDto);
    }
}

