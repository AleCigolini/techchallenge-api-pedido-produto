package br.com.fiap.techchallengeapipedidoproduto.pedido.presentation.rest;

import br.com.fiap.techchallengeapipedidoproduto.pedido.application.controller.PedidoController;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRecebidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.PedidoRequestDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.response.PedidoResponseDto;
import br.com.fiap.techchallengeapipedidoproduto.pedido.presentation.rest.interfaces.PedidoRestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoRestControllerImpl implements PedidoRestController {

    private final PedidoController pedidoController;

    @Override
    @GetMapping
    public ResponseEntity<List<PedidoResponseDto>> buscarPedidos(@RequestParam(value="status", required=false) List<String> status) {
        List<PedidoResponseDto> pedidosResponseDTO = pedidoController.buscarPedidos(status);

        return ResponseEntity.ok(pedidosResponseDTO);
    }

    @Override
    @PostMapping
    public ResponseEntity<PedidoResponseDto> criarPedido(@RequestBody @Valid PedidoRequestDto pedidoRequestDTO, HttpServletRequest request) throws URISyntaxException {
        String idCliente = request.getHeader("x-cliente-id");
        PedidoResponseDto pedidoResponse = pedidoController.criarPedido(pedidoRequestDTO, idCliente);

        return ResponseEntity.created(new URI("/pedidos/" + pedidoResponse.getId())).body(pedidoResponse);
    }

    @Override
    @PatchMapping("/recebido/{id}")
    public ResponseEntity<PedidoResponseDto> atualizarPedidoRecebido(@RequestBody @Valid PedidoRecebidoRequestDto pedidoRecebidoRequestDTO, @PathVariable String id) {
        PedidoResponseDto pedidoResponseDTO = pedidoController.atualizarPedidoRecebido(pedidoRecebidoRequestDTO, id);

        return ResponseEntity.ok(pedidoResponseDTO);
    }
}