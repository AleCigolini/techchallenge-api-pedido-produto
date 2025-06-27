package br.com.fiap.techchallengeapipedidoproduto.pedido.presentation.rest.interfaces;

import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoStatusRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.WebhookNotificationRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.response.PedidoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URISyntaxException;
import java.util.List;

@Tag(name = "${tag.swagger.pedido.name}", description = "${tag.swagger.pedido.description}")
public interface PedidoRestController {

    /**
     * Busca todos os pedidos
     *
     * @return {@link PedidoResponseDto}
     */
    @Operation(summary = "Buscar todos os pedidos",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(responseCode = "400", description = "Erros de validação",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Pedido não encntrado",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    ResponseEntity<List<PedidoResponseDto>> buscarPedidos(@Parameter(name = "status", required=false, description = "ABERTO, RECEBIDO, EM_PREPARACAO, PRONTO, FINALIZADO, CANCELADO") List<String> status);

    /**
     * Criar pedido
     *
     * @param pedidoRequestDTO DTO para criação de pedido
     * @return {@link PedidoResponseDto}
     */
    @Operation(summary = "Criar novo pedido",
            responses = {
                    @ApiResponse(responseCode = "201"),
                    @ApiResponse(responseCode = "400", description = "Erro no cadastro do pedido/Erros de validação",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
            })
    ResponseEntity<PedidoResponseDto> criarPedido(PedidoRequestDto pedidoRequestDTO) throws URISyntaxException;

    /**
     * Atualizar status do pedido
     *
     * @param pedidoStatusRequestDTO DTO para atualização do status do pedido
     * @param id ID do pedido a ter seu status atualizado
     * @return {@link PedidoResponseDto}
     */
    @Operation(summary = "Atualizar o status de um pedido")
    ResponseEntity<PedidoResponseDto> atualizarStatusPedido(PedidoStatusRequestDto pedidoStatusRequestDTO, String id);

    /**
     * Recebe e processa notificações relacionadas ao pagamento dos pedidos
     *
     * @param notificacao Objeto com os dados da notificação
     */
    @Operation(summary = "Recebe e processa notificações relacionadas ao pagamento dos pedidos",
            responses = {
                    @ApiResponse(responseCode = "202"),
                    @ApiResponse(responseCode = "400", description = "Erros de validação",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "404", description = "Pagamento ou pedido não encontrado",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    ),
                    @ApiResponse(responseCode = "500", description = "Erro no procesamento da notificação de pagamento",
                            content = @Content(schema = @Schema(ref = "Problema"))
                    )
            })
    void webhookMercadoPago(@RequestBody WebhookNotificationRequestDto notificacao);
}
