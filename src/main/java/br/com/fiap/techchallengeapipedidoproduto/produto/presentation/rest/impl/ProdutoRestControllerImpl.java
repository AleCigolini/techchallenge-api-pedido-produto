package br.com.fiap.techchallengeapipedidoproduto.produto.presentation.rest.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.controller.ProdutoController;
import br.com.fiap.techchallengeapipedidoproduto.produto.presentation.rest.ProdutoRestController;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.ProdutoResponseDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.ProdutoRequestDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoRestControllerImpl implements ProdutoRestController {

    private final ProdutoController produtoController;

    @Override
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutos() {
        List<ProdutoResponseDTO> produtosResponseDTO = produtoController.buscarProdutos();

        return ResponseEntity.ok(produtosResponseDTO);
    }

    @Override
    @GetMapping("/categoria/{idCategoriaProduto}")
    public ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorCategoria(@PathVariable String idCategoriaProduto) {
        List<ProdutoResponseDTO> produtosResponse = produtoController.buscarProdutosPorCategoria(idCategoriaProduto);

        return ResponseEntity.ok(produtosResponse);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(@PathVariable String id) {
        ProdutoResponseDTO produtoResponseDTO = produtoController.buscarProdutoPorId(id);

        return ResponseEntity.ok(produtoResponseDTO);
    }

    @Override
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO) throws URISyntaxException {
        ProdutoResponseDTO produtoResponse = produtoController.criarProduto(produtoRequestDTO);

        return ResponseEntity.created(new URI("/produtos/" + produtoResponse.getId())).body(produtoResponse);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequestDTO, @PathVariable String id) {
        ProdutoResponseDTO produtoResponse = produtoController.atualizarProduto(produtoRequestDTO, id);

        return ResponseEntity.ok(produtoResponse);
    }

    @Override
    @DeleteMapping("/{idProduto}")
    public ResponseEntity<Void> excluirProduto(@PathVariable String idProduto) {
        produtoController.excluirProduto(idProduto);

        return ResponseEntity.ok().build();
    }
}
