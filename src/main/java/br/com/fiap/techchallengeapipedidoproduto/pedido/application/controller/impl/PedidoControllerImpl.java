package br.com.fiap.techchallengeapipedidoproduto.pedido.application.controller.impl;

//import br.com.fiap.techchallengeapipedidoproduto.cliente.application.gateway.ClienteGateway;
//import br.com.fiap.techchallengeapipedidoproduto.cliente.application.gateway.impl.ClienteGatewayImpl;
//import br.com.fiap.techchallengeapipedidoproduto.cliente.application.mapper.DatabaseClienteMapper;
//import br.com.fiap.techchallengeapipedidoproduto.cliente.application.usecase.ConsultarClienteUseCase;
//import br.com.fiap.techchallengeapipedidoproduto.cliente.application.usecase.impl.ConsultarClienteUseCaseImpl;
//import br.com.fiap.techchallengeapipedidoproduto.cliente.common.interfaces.ClienteDatabase;
import br.com.fiap.techchallengeapipedidoproduto.core.config.properties.MercadoPagoProperties;
//import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.gateway.PagamentoGateway;
//import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.gateway.impl.PagamentoGatewayImpl;
//import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.mapper.DatabasePagamentoMapper;
//import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.SalvarPagamentoUseCase;
//import br.com.fiap.techchallengeapipedidoproduto.pagamento.application.usecase.impl.SalvarPagamentoUseCaseImpl;
//import br.com.fiap.techchallengeapipedidoproduto.pagamento.common.interfaces.PagamentoDatabase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.controller.PedidoController;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway.PedidoGateway;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.gateway.impl.PedidoGatewayImpl;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.mapper.DatabasePedidoMapper;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.mapper.RequestPedidoMapper;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.presenter.PedidoPresenter;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.ConsultarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.CriarPedidoMercadoPagoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.ProcessarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.SalvarPedidoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl.ConsultarPedidoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl.CriarPedidoMercadoPagoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl.ProcessarPedidoMercadoPagoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase.impl.SalvarPedidoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoStatusRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.WebhookNotificationRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.response.PedidoResponseDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.interfaces.PedidoDatabase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.Pedido;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.client.mercadopago.MercadoPagoCodigoQRClient;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.client.mercadopago.MercadoPagoMerchantOrdersClient;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.client.mercadopago.mapper.MercadoPagoOrderRequestMapper;
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
    private final ProcessarPedidoUseCase processarPedidoUseCase;
    private final RequestPedidoMapper requestPedidoMapper;
    private final PedidoPresenter pedidoPresenter;

    public PedidoControllerImpl(PedidoDatabase pedidoDatabase,
                                ProdutoDatabase produtoDatabase,
//                                ClienteDatabase clienteDatabase,
//                                PagamentoDatabase pagamentoDatabase,
                                ProdutoMapper produtoMapper,
                                RequestPedidoMapper requestPedidoMapper,
//                                DatabaseClienteMapper databaseClienteMapper,
                                DatabasePedidoMapper databasePedidoMapper,
//                                DatabasePagamentoMapper databasePagamentoMapper,
                                MercadoPagoOrderRequestMapper mercadoPagoOrderRequestMapper,
                                PedidoPresenter pedidoPresenter,
                                MercadoPagoCodigoQRClient mercadoPagoCodigoQRClient,
                                MercadoPagoMerchantOrdersClient mercadoPagoMerchantOrdersClient,
                                MercadoPagoProperties mercadoPagoProperties
    ) {
//        final PagamentoGateway pagamentoGateway = new PagamentoGatewayImpl(pagamentoDatabase, databasePagamentoMapper);
//        final SalvarPagamentoUseCase salvarPagamentoUseCase = new SalvarPagamentoUseCaseImpl(pagamentoGateway);
//
//        final ClienteGateway clienteGateway = new ClienteGatewayImpl(clienteDatabase, databaseClienteMapper);
//        final ConsultarClienteUseCase consultarClienteUseCase = new ConsultarClienteUseCaseImpl(clienteGateway);

        final ProdutoGateway produtoGateway = new ProdutoGatewayImpl(produtoDatabase, produtoMapper);
        final BuscarProdutoUseCase buscarProdutoUseCase = new BuscarProdutoUseCaseImpl(produtoGateway);

        final CriarPedidoMercadoPagoUseCase criarPedidoMercadoPagoUseCase = new CriarPedidoMercadoPagoUseCaseImpl(mercadoPagoOrderRequestMapper, mercadoPagoCodigoQRClient, mercadoPagoProperties);

        final PedidoGateway pedidoGateway = new PedidoGatewayImpl(pedidoDatabase, databasePedidoMapper);
        this.salvarPedidoUseCase = new SalvarPedidoUseCaseImpl(pedidoGateway, buscarProdutoUseCase, criarPedidoMercadoPagoUseCase);
        this.consultarPedidoUseCase = new ConsultarPedidoUseCaseImpl(pedidoGateway);
        this.processarPedidoUseCase = new ProcessarPedidoMercadoPagoUseCaseImpl(consultarPedidoUseCase, salvarPedidoUseCase, mercadoPagoMerchantOrdersClient, mercadoPagoProperties);
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
    public PedidoResponseDto criarPedido(PedidoRequestDto pedidoRequestDto) {
        Pedido pedido = salvarPedidoUseCase.criarPedido(requestPedidoMapper.pedidoRequestDtoParaPedido(pedidoRequestDto));

        return pedidoPresenter.pedidoParaPedidoResponseDTO(pedido);
    }

    @Override
    public PedidoResponseDto atualizarStatusPedido(PedidoStatusRequestDto pedidoStatusRequestDTO, String id) {
        Pedido pedido = salvarPedidoUseCase.atualizarStatusPedido(pedidoPresenter.statusPedidoParaStatusPedidoEnum(pedidoStatusRequestDTO.getStatus()), id);

        return pedidoPresenter.pedidoParaPedidoResponseDTO(pedido);
    }

    @Override
    public void processarNotificacao(WebhookNotificationRequestDto notificacao) {
        processarPedidoUseCase.processarNotificacao(notificacao);
    }
}