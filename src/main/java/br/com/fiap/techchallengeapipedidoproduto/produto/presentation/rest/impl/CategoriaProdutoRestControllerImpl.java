package br.com.fiap.techchallengeapipedidoproduto.produto.presentation.rest.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.controller.CategoriaProdutoController;
import br.com.fiap.techchallengeapipedidoproduto.produto.presentation.rest.CategoriaProdutoRestController;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.CategoriaProdutoResponseDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.CategoriaProdutoRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/categorias-produto")
@RequiredArgsConstructor
public class CategoriaProdutoRestControllerImpl implements CategoriaProdutoRestController {

    private final CategoriaProdutoController categoriaProdutoController;

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProduto> buscarCategoriaProduto(@PathVariable String id) {
        return ResponseEntity.ok(categoriaProdutoController.buscarCategoriaProdutoPorId(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CategoriaProdutoResponseDTO>> buscarCategoriasProduto() {
        List<CategoriaProdutoResponseDTO> categoriasProdutoResponseDTO = categoriaProdutoController.buscarCategoriasProduto();

        return ResponseEntity.ok(categoriasProdutoResponseDTO);
    }

    @Override
    @PostMapping
    public ResponseEntity<CategoriaProdutoResponseDTO> criarCategoriaProduto(@RequestBody CategoriaProdutoRequestDTO categoriaProdutoRequestDto) throws URISyntaxException {
        CategoriaProdutoResponseDTO categoriaProdutoResponseDTO = categoriaProdutoController.criarCategoriaProduto(categoriaProdutoRequestDto);

        return ResponseEntity.created(new URI("/categorias-produto/" + categoriaProdutoResponseDTO.getId())).body(categoriaProdutoResponseDTO);
    }
}
