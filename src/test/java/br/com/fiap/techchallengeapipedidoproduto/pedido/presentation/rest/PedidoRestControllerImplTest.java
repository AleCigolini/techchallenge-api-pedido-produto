package br.com.fiap.techchallengeapipedidoproduto.pedido.presentation.rest;

import br.com.fiap.techchallengeapipedidoproduto.pedido.application.controller.PedidoController;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRecebidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.response.PedidoResponseDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PedidoRestControllerImplTest {

    private PedidoController pedidoController;
    private PedidoRestControllerImpl restController;

    @BeforeEach
    public void setUp() {
        pedidoController = Mockito.mock(PedidoController.class);
        restController = new PedidoRestControllerImpl(pedidoController);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(pedidoController);
    }

    @Test
    public void deveBuscarTodosOsPedidosRetornandoResponseOk() {
        // given
        List<PedidoResponseDto> pedidosEsperados = Arrays.asList(
                criarPedidoResponseDto("1", "ABC123", StatusPedidoEnum.ABERTO.toString()),
                criarPedidoResponseDto("2", "DEF456", StatusPedidoEnum.RECEBIDO.toString())
        );

        when(pedidoController.buscarPedidos(null)).thenReturn(pedidosEsperados);

        // when
        ResponseEntity<List<PedidoResponseDto>> response = restController.buscarPedidos(null);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidosEsperados, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(pedidoController).buscarPedidos(null);
    }

    @Test
    public void deveBuscarPedidosFiltrandoPorStatus() {
        // given
        List<String> filtroStatus = Collections.singletonList(StatusPedidoEnum.ABERTO.toString());

        List<PedidoResponseDto> pedidosEsperados = Collections.singletonList(
                criarPedidoResponseDto("1", "ABC123", StatusPedidoEnum.ABERTO.toString())
        );

        when(pedidoController.buscarPedidos(filtroStatus)).thenReturn(pedidosEsperados);

        // when
        ResponseEntity<List<PedidoResponseDto>> response = restController.buscarPedidos(filtroStatus);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidosEsperados, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(pedidoController).buscarPedidos(filtroStatus);
    }

    @Test
    public void deveAtualizarPedidoRecebidoRetornandoOk() {
        // given
        String idPedido = UUID.randomUUID().toString();
        PedidoRecebidoRequestDto pedidoRecebidoRequestDto = new PedidoRecebidoRequestDto();
        pedidoRecebidoRequestDto.setCodigoPagamento("123");

        PedidoResponseDto pedidoResponse = criarPedidoResponseDto(idPedido, "ABC123", StatusPedidoEnum.RECEBIDO.toString());

        when(pedidoController.atualizarPedidoRecebido(pedidoRecebidoRequestDto, idPedido)).thenReturn(pedidoResponse);

        // when
        ResponseEntity<PedidoResponseDto> response = restController.atualizarPedidoRecebido(pedidoRecebidoRequestDto, idPedido);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidoResponse, response.getBody());
        assertNotNull(response.getBody());
        assertEquals(StatusPedidoEnum.RECEBIDO.toString(), response.getBody().getStatus().toString());
        verify(pedidoController).atualizarPedidoRecebido(pedidoRecebidoRequestDto, idPedido);
    }

    private PedidoResponseDto criarPedidoResponseDto(String id, String codigo, String status) {
        PedidoResponseDto pedido = new PedidoResponseDto();
        pedido.setId(id);
        pedido.setCodigo(codigo);
        pedido.setStatus(StatusPedidoEnum.valueOf(status));
        pedido.setPreco(new BigDecimal("79.90"));
        return pedido;
    }
}
