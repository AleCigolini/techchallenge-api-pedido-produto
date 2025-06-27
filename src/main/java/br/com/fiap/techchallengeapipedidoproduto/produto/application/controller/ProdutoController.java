package br.com.fiap.techchallengeapipedidoproduto.produto.application.controller;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.ProdutoResponseDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.ProdutoRequestDTO;

import java.util.List;

public interface ProdutoController {
    List<ProdutoResponseDTO> buscarProdutos();

    List<ProdutoResponseDTO> buscarProdutosPorCategoria(String idCategoriaProduto);

    ProdutoResponseDTO buscarProdutoPorId(String id);

    ProdutoResponseDTO criarProduto(ProdutoRequestDTO produtoRequestDTO);

    ProdutoResponseDTO atualizarProduto(ProdutoRequestDTO produtoRequestDTO, String id);

    void excluirProduto(String idProduto);
}
