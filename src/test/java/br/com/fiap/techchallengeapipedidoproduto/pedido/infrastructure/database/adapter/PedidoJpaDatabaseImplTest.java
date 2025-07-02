package br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.database.adapter;

import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.entity.JpaPedidoEntity;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.entity.JpaProdutoPedidoEntity;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.database.repository.JpaPedidoRepository;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.database.repository.JpaProdutoPedidoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PedidoJpaDatabaseImplTest {

    private JpaPedidoRepository jpaPedidoRepository;
    private JpaProdutoPedidoRepository jpaProdutoPedidoRepository;
    private PedidoJpaDatabaseImpl pedidoJpaDatabase;

    @BeforeEach
    public void setUp() {
        jpaPedidoRepository = Mockito.mock(JpaPedidoRepository.class);
        jpaProdutoPedidoRepository = Mockito.mock(JpaProdutoPedidoRepository.class);
        pedidoJpaDatabase = new PedidoJpaDatabaseImpl(jpaPedidoRepository, jpaProdutoPedidoRepository);
    }

    @AfterEach
    public void tearDown() {
        Mockito.reset();
    }

    @Test
    public void deveBuscarTodosOsPedidosSemFiltro() {
        // given
        List<JpaPedidoEntity> pedidosEsperados = Arrays.asList(
                criarJpaPedidoEntity(UUID.randomUUID(), "ABC123", StatusPedidoEnum.ABERTO.toString()),
                criarJpaPedidoEntity(UUID.randomUUID(), "DEF456", StatusPedidoEnum.RECEBIDO.toString())
        );

        when(jpaPedidoRepository.findAll()).thenReturn(pedidosEsperados);

        // when
        List<JpaPedidoEntity> resultado = pedidoJpaDatabase.buscarPedidos(null);

        // then
        assertEquals(2, resultado.size());
        assertEquals(pedidosEsperados, resultado);
        verify(jpaPedidoRepository).findAll();
    }

    @Test
    public void deveBuscarPedidosFiltrandoPorStatus() {
        // given
        List<StatusPedidoEnum> filtroStatus = Arrays.asList(StatusPedidoEnum.ABERTO, StatusPedidoEnum.RECEBIDO);
        List<String> filtroStatusString = Arrays.asList(StatusPedidoEnum.ABERTO.toString(), StatusPedidoEnum.RECEBIDO.toString());

        List<JpaPedidoEntity> pedidosEsperados = Arrays.asList(
                criarJpaPedidoEntity(UUID.randomUUID(), "ABC123", StatusPedidoEnum.ABERTO.toString()),
                criarJpaPedidoEntity(UUID.randomUUID(), "DEF456", StatusPedidoEnum.RECEBIDO.toString())
        );

        when(jpaPedidoRepository.findAllByStatusIn(filtroStatusString)).thenReturn(pedidosEsperados);

        // when
        List<JpaPedidoEntity> resultado = pedidoJpaDatabase.buscarPedidos(filtroStatus);

        // then
        assertEquals(2, resultado.size());
        assertEquals(pedidosEsperados, resultado);
        verify(jpaPedidoRepository).findAllByStatusIn(filtroStatusString);
    }

    @Test
    public void deveCriarPedidoComProdutos() {
        // given
        UUID idPedido = UUID.randomUUID();
        JpaPedidoEntity pedido = criarJpaPedidoEntity(idPedido, "ABC123", StatusPedidoEnum.ABERTO.toString());

        JpaProdutoPedidoEntity produto1 = new JpaProdutoPedidoEntity();
        JpaProdutoPedidoEntity produto2 = new JpaProdutoPedidoEntity();
        List<JpaProdutoPedidoEntity> produtos = Arrays.asList(produto1, produto2);
        pedido.setProdutos(produtos);

        when(jpaPedidoRepository.save(pedido)).thenReturn(pedido);

        // when
        JpaPedidoEntity resultado = pedidoJpaDatabase.criarPedido(pedido);

        // then
        assertNotNull(resultado);
        assertEquals(pedido, resultado);
        verify(jpaPedidoRepository).save(pedido);
        verify(jpaProdutoPedidoRepository, times(2)).save(any(JpaProdutoPedidoEntity.class));

        // Verificar que o pedido foi atribuído aos produtos
        assertEquals(pedido, produto1.getPedido());
        assertEquals(pedido, produto2.getPedido());
    }

    @Test
    public void deveSalvarPedido() {
        // given
        UUID idPedido = UUID.randomUUID();
        JpaPedidoEntity pedido = criarJpaPedidoEntity(idPedido, "ABC123", StatusPedidoEnum.ABERTO.toString());

        when(jpaPedidoRepository.save(pedido)).thenReturn(pedido);

        // when
        JpaPedidoEntity resultado = pedidoJpaDatabase.salvarPedido(pedido);

        // then
        assertNotNull(resultado);
        assertEquals(pedido, resultado);
        verify(jpaPedidoRepository).save(pedido);
    }

    @Test
    public void deveBuscarPedidoPorId() {
        // given
        UUID idPedido = UUID.randomUUID();
        JpaPedidoEntity pedido = criarJpaPedidoEntity(idPedido, "ABC123", StatusPedidoEnum.ABERTO.toString());

        when(jpaPedidoRepository.findById(idPedido)).thenReturn(Optional.of(pedido));

        // when
        Optional<JpaPedidoEntity> resultado = pedidoJpaDatabase.buscarPedidoPorId(idPedido.toString());

        // then
        assertTrue(resultado.isPresent());
        assertEquals(pedido, resultado.get());
        verify(jpaPedidoRepository).findById(idPedido);
    }

    private JpaPedidoEntity criarJpaPedidoEntity(UUID id, String codigo, String status) {
        JpaPedidoEntity pedido = new JpaPedidoEntity();
        pedido.setId(id);
        pedido.setCodigo(codigo);
        pedido.setStatus(status);
        pedido.setPreco(new BigDecimal("79.90"));
        pedido.setProdutos(new ArrayList<>());
        return pedido;
    }
}


