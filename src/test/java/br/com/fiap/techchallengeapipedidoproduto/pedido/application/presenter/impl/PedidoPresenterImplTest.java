package br.com.fiap.techchallengeapipedidoproduto.pedido.application.presenter.impl;

import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.response.PedidoResponseDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PedidoPresenterImplTest {

    private ModelMapper modelMapper;
    private PedidoPresenterImpl presenter;
    private TypeMap<PedidoResponseDto, Pedido> typeMap;

    @BeforeEach
    public void setUp() {
        modelMapper = Mockito.mock(ModelMapper.class);
        typeMap = Mockito.mock(TypeMap.class);
        presenter = new PedidoPresenterImpl(modelMapper);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset();
    }

    @Test
    public void deveConverterPedidosParaPedidoResponseDTOs() {
        // given
        List<Pedido> pedidos = Arrays.asList(new Pedido(), new Pedido());
        when(modelMapper.map(any(Pedido.class), eq(PedidoResponseDto.class)))
                .thenReturn(new PedidoResponseDto());

        // when
        List<PedidoResponseDto> result = presenter.pedidosParaPedidoResponseDTOs(pedidos);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(modelMapper, Mockito.times(2)).map(any(Pedido.class), eq(PedidoResponseDto.class));
    }

    @Test
    public void deveConverterStatusPedidoTextParaStatusPedidoEnums() {
        // given
        List<String> statusTextos = Arrays.asList("Aberto", "Recebido");

        // when
        List<StatusPedidoEnum> result = presenter.statusPedidoTextParaStatusPedidoEnums(statusTextos);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(StatusPedidoEnum.ABERTO, result.get(0));
        assertEquals(StatusPedidoEnum.RECEBIDO, result.get(1));
    }

    @Test
    public void deveConverterStatusPedidoParaStatusPedidoEnums() {
        // given
        List<String> status = Arrays.asList("ABERTO", "RECEBIDO");

        // when
        List<StatusPedidoEnum> result = presenter.statusPedidoParaStatusPedidoEnums(status);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(StatusPedidoEnum.ABERTO, result.get(0));
        assertEquals(StatusPedidoEnum.RECEBIDO, result.get(1));
    }

    @Test
    public void deveConverterStatusPedidoParaStatusPedidoEnum() {
        // given
        String status = "ABERTO";

        // when
        StatusPedidoEnum result = presenter.statusPedidoParaStatusPedidoEnum(status);

        // then
        assertEquals(StatusPedidoEnum.ABERTO, result);
    }

    @Test
    public void deveConverterPedidoParaPedidoResponseDTO() {
        // given
        Pedido pedido = new Pedido();
        when(modelMapper.map(pedido, PedidoResponseDto.class)).thenReturn(new PedidoResponseDto());

        // when
        PedidoResponseDto result = presenter.pedidoParaPedidoResponseDTO(pedido);

        // then
        assertNotNull(result);
        verify(modelMapper).map(pedido, PedidoResponseDto.class);
    }

    @Test
    public void deveConverterPedidoRequestDtoParaPedido() {
        // given
        PedidoResponseDto pedidoResponseDto = new PedidoResponseDto();
        when(modelMapper.typeMap(PedidoResponseDto.class, Pedido.class)).thenReturn(typeMap);
        when(modelMapper.map(pedidoResponseDto, Pedido.class)).thenReturn(new Pedido());

        // when
        Pedido result = presenter.pedidoRequestDtoParaPedido(pedidoResponseDto);

        // then
        assertNotNull(result);
        verify(modelMapper).typeMap(PedidoResponseDto.class, Pedido.class);
        verify(modelMapper).map(pedidoResponseDto, Pedido.class);
    }
}
