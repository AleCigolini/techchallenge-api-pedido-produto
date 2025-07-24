package br.com.fiap.techchallengeapipedidoproduto.pedido.presentation.rest.interfaces;

import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRecebidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.response.PedidoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

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
    ResponseEntity<PedidoResponseDto> criarPedido(PedidoRequestDto pedidoRequestDTO, HttpServletRequest request) throws URISyntaxException;

    /**
     * Atualizar status do pedido
     *
     * @param pedidoRecebidoRequestDTO DTO para atualização do status do pedido
     * @param id ID do pedido a ter seu status atualizado
     * @return {@link PedidoResponseDto}
     */
    @Operation(summary = "Atualizar o status de um pedido")
    ResponseEntity<PedidoResponseDto> atualizarPedidoRecebido(PedidoRecebidoRequestDto pedidoRecebidoRequestDTO, String id);
}
