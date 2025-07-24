package br.com.fiap.techchallengeapipedidoproduto.pedido.application.controller.impl;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.infrastructure.client.PagamentoClient;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.mapper.DatabasePedidoMapper;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.mapper.RequestPedidoMapper;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.presenter.PedidoPresenter;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.ConsultarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.SalvarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRecebidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.interfaces.PedidoDatabase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.ProdutoMapper;
import br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adapter.ProdutoDatabase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;

public class PedidoControllerImplTest {

    private SalvarPedidoUseCase salvarPedidoUseCase;
    private ConsultarPedidoUseCase consultarPedidoUseCase;
    private PedidoControllerImpl controller;

    @BeforeEach
    public void setUp() {
        PedidoDatabase pedidoDatabase = Mockito.mock(PedidoDatabase.class);
        ProdutoDatabase produtoDatabase = Mockito.mock(ProdutoDatabase.class);
        ProdutoMapper produtoMapper = Mockito.mock(ProdutoMapper.class);
        salvarPedidoUseCase = Mockito.mock(SalvarPedidoUseCase.class);
        consultarPedidoUseCase = Mockito.mock(ConsultarPedidoUseCase.class);
        PedidoPresenter pedidoPresenter = Mockito.mock(PedidoPresenter.class);
        RequestPedidoMapper requestPedidoMapper = Mockito.mock(RequestPedidoMapper.class);
        DatabasePedidoMapper databasePedidoMapper = Mockito.mock(DatabasePedidoMapper.class);
        PagamentoClient pagamentoClient = Mockito.mock(PagamentoClient.class);

        controller = new PedidoControllerImpl(
                pedidoDatabase,
                produtoDatabase,
                produtoMapper,
                requestPedidoMapper,
                databasePedidoMapper,
                pedidoPresenter,
                pagamentoClient
        );

        ReflectionTestUtils.setField(controller, "consultarPedidoUseCase", consultarPedidoUseCase);
        ReflectionTestUtils.setField(controller, "salvarPedidoUseCase", salvarPedidoUseCase);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset();
    }

    @Test
    public void deveBuscarTodosOsPedidos() {
        // when
        controller.buscarPedidos(null);

        // then
        Mockito.verify(consultarPedidoUseCase, Mockito.atLeastOnce()).buscarPedidos(any());
    }

    @Test
    public void deveBuscarPedidosFiltrandoPorStatus() {
        // arrange
        List<String> status = Arrays.asList(StatusPedidoEnum.ABERTO.toString(), StatusPedidoEnum.RECEBIDO.toString());

        // when
        controller.buscarPedidos(status);

        // then
        Mockito.verify(consultarPedidoUseCase, Mockito.atLeastOnce()).buscarPedidos(any());
    }

    @Test
    public void deveCriarPedido() {
        // arrange
        PedidoRequestDto pedidoRequestDto = new PedidoRequestDto();
        var idCliente = UUID.randomUUID().toString();

        // when
        controller.criarPedido(pedidoRequestDto, idCliente);

        // then
        Mockito.verify(salvarPedidoUseCase, Mockito.atLeastOnce()).criarPedido(any(), any());
    }

    @Test
    public void deveAtualizarPedidoRecebido() {
        // arrange
        PedidoRecebidoRequestDto pedidoRecebidoRequestDto = new PedidoRecebidoRequestDto();

        // when
        controller.atualizarPedidoRecebido(pedidoRecebidoRequestDto, "idTest");

        // then
        Mockito.verify(salvarPedidoUseCase, Mockito.atLeastOnce()).atualizarPedidoRecebido(any(), any());
    }
}
