package br.com.fiap.techchallengeapipedidoproduto.produto.application.controller;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.CategoriaProdutoRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.CategoriaProdutoResponseDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;

import java.util.List;

public interface CategoriaProdutoController {
    CategoriaProduto buscarCategoriaProdutoPorId(String id);

    List<CategoriaProdutoResponseDTO> buscarCategoriasProduto();

    CategoriaProdutoResponseDTO criarCategoriaProduto(CategoriaProdutoRequestDTO categoriaProdutoRequestDto);

}
