package br.com.fiap.techchallengeapipedidoproduto.pedido.application.controller.mapper.model;

import br.com.fiap.techchallengeapipedidoproduto.pedido.application.mapper.model.DatabasePedidoModelMapper;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.entity.JpaPedidoEntity;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class DatabasePedidoModelMapperTest {

    private ModelMapper modelMapper;
    private DatabasePedidoModelMapper mapper;

    @BeforeEach
    public void setUp() {
        modelMapper = Mockito.mock(ModelMapper.class);
        mapper = new DatabasePedidoModelMapper(modelMapper);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset(modelMapper);
    }

    @Test
    public void deveConverterPedidoParaJpaPedidoEntity() {
        // given
        Pedido pedido = new Pedido();
        when(modelMapper.map(pedido, JpaPedidoEntity.class)).thenReturn(new JpaPedidoEntity());

        // when
        JpaPedidoEntity result = mapper.pedidoParaJpaPedidoEntity(pedido);

        // then
        assertNotNull(result);
    }

    @Test
    public void deveConverterJpaPedidoEntityParaPedido() {
        // given
        JpaPedidoEntity entity = new JpaPedidoEntity();
        when(modelMapper.map(entity, Pedido.class)).thenReturn(new Pedido());

        // when
        Pedido result = mapper.jpaPedidoEntityParaPedido(entity);

        // then
        assertNotNull(result);
    }

    @Test
    public void deveConverterJpaPedidosEntityParaPedidos() {
        // given
        List<JpaPedidoEntity> entities = Collections.singletonList(new JpaPedidoEntity());
        when(modelMapper.map(any(JpaPedidoEntity.class), any())).thenReturn(new Pedido());

        // when
        List<Pedido> result = mapper.jpaPedidosEntityParaPedidos(entities);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
