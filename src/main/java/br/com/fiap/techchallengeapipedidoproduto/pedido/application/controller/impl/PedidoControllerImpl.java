package br.com.fiap.techchallengeapipedidoproduto.pedido.application.controller.impl;

import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.CriarPedidoMercadoPagoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.impl.CriarPedidoMercadoPagoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.pagamento.infrastructure.client.PagamentoClient;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.controller.PedidoController;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway.PedidoGateway;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway.impl.PedidoGatewayImpl;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.mapper.DatabasePedidoMapper;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.mapper.RequestPedidoMapper;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.presenter.PedidoPresenter;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.ConsultarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.SalvarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl.ConsultarPedidoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl.SalvarPedidoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRecebidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.response.PedidoResponseDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.interfaces.PedidoDatabase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.ProdutoGateway;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.impl.ProdutoGatewayImpl;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.ProdutoMapper;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.BuscarProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl.BuscarProdutoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adapter.ProdutoDatabase;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoControllerImpl implements PedidoController {

    private final SalvarPedidoUseCase salvarPedidoUseCase;
    private final ConsultarPedidoUseCase consultarPedidoUseCase;
    private final RequestPedidoMapper requestPedidoMapper;
    private final PedidoPresenter pedidoPresenter;

    public PedidoControllerImpl(PedidoDatabase pedidoDatabase,
                                ProdutoDatabase produtoDatabase,
                                ProdutoMapper produtoMapper,
                                RequestPedidoMapper requestPedidoMapper,
                                DatabasePedidoMapper databasePedidoMapper,
                                PedidoPresenter pedidoPresenter,
                                PagamentoClient pagamentoClient
    ) {
        final ProdutoGateway produtoGateway = new ProdutoGatewayImpl(produtoDatabase, produtoMapper);
        final BuscarProdutoUseCase buscarProdutoUseCase = new BuscarProdutoUseCaseImpl(produtoGateway);

        final CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase = new CriarPedidoMercadoPagoUseCaseImpl(pagamentoClient);

        final PedidoGateway pedidoGateway = new PedidoGatewayImpl(pedidoDatabase, databasePedidoMapper);
        this.salvarPedidoUseCase = new SalvarPedidoUseCaseImpl(pedidoGateway, buscarProdutoUseCase, criarPedidoMercadoPagoUseCase);
        this.consultarPedidoUseCase = new ConsultarPedidoUseCaseImpl(pedidoGateway);
        this.requestPedidoMapper = requestPedidoMapper;
        this.pedidoPresenter = pedidoPresenter;
    }

    @Override
    public List<PedidoResponseDto> buscarPedidos(List<String> status) {
        List<StatusPedidoEnum> statusPedidoEnums = status == null || status.isEmpty() ? null : pedidoPresenter.statusPedidoParaStatusPedidoEnums(status);

        List<Pedido> pedidos = consultarPedidoUseCase.buscarPedidos(statusPedidoEnums);

        return pedidoPresenter.pedidosParaPedidoResponseDTOs(pedidos);
    }

    @Override
    public PedidoResponseDto criarPedido(PedidoRequestDto pedidoRequestDto, String idCliente) {
        Pedido pedido = salvarPedidoUseCase.criarPedido(requestPedidoMapper.pedidoRequestDtoParaPedido(pedidoRequestDto), idCliente);

        return pedidoPresenter.pedidoParaPedidoResponseDTO(pedido);
    }

    @Override
    public PedidoResponseDto atualizarPedidoRecebido(PedidoRecebidoRequestDto pedidoRecebidoRequestDTO, String idPedido) {
        Pedido pedido = salvarPedidoUseCase.atualizarPedidoRecebido(idPedido, pedidoRecebidoRequestDTO.getCodigoPagamento());

        return pedidoPresenter.pedidoParaPedidoResponseDTO(pedido);
    }
}