package br.com.fiap.techchallengeapipedidoproduto.pedido.application.mapper.model;

import br.com.fiap.techchallengeapipedidoproduto.core.config.exception.exceptions.ValidacaoEntidadeException;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.ErrorMessage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RequestPedidoModelMapperTest {

    private ModelMapper modelMapper;
    private RequestPedidoModelMapper mapper;

    @BeforeEach
    public void setUp() {
        modelMapper = Mockito.mock(ModelMapper.class);
        mapper = new RequestPedidoModelMapper(modelMapper);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(modelMapper);
    }

    @Test
    public void deveConverterPedidoRequestDtoParaPedido() {
        // given
        PedidoRequestDto pedidoRequestDto = new PedidoRequestDto();
        when(modelMapper.map(pedidoRequestDto, Pedido.class)).thenReturn(new Pedido());

        // when
        Pedido result = mapper.pedidoRequestDtoParaPedido(pedidoRequestDto);

        // then
        assertNotNull(result);
    }

    @Test
    public void deveLancarValidacaoEntidadeExceptionAoConverterPedidoRequestDtoParaPedido() {
        // given
        PedidoRequestDto pedidoRequestDto = new PedidoRequestDto();
        MappingException mappingException = new MappingException(List.of(new ErrorMessage("Erro de validação", new ValidacaoEntidadeException("Erro de validação"))));
        when(modelMapper.map(pedidoRequestDto, Pedido.class)).thenThrow(mappingException);

        // when
        ValidacaoEntidadeException exception = assertThrows(ValidacaoEntidadeException.class, () -> mapper.pedidoRequestDtoParaPedido(pedidoRequestDto));

        // then
        assertEquals("Erro de validação", exception.getMessage());
    }

    @Test
    public void deveLancarMappingExceptionAoConverterPedidoRequestDtoParaPedido() {
        // given
        PedidoRequestDto pedidoRequestDto = new PedidoRequestDto();
        MappingException mappingException = new MappingException(List.of(new ErrorMessage("Erro de validação")));

        when(modelMapper.map(pedidoRequestDto, Pedido.class)).thenThrow(mappingException);

        // when
        // then
        assertThrows(MappingException.class, () -> mapper.pedidoRequestDtoParaPedido(pedidoRequestDto));
    }
}
