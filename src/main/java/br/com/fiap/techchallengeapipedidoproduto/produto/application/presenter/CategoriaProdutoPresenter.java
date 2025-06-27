package br.com.fiap.techchallengeapipedidoproduto.produto.application.presenter;

import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.CategoriaProdutoResponseDTO;

import java.util.List;

public interface CategoriaProdutoPresenter {

    CategoriaProdutoResponseDTO categoriaProdutoParaCategoriaProdutoResponseDTO(CategoriaProduto categoriaProduto);

    List<CategoriaProdutoResponseDTO> categoriasProdutoParaCategoriasProdutoResponseDTO(List<CategoriaProduto> categoriasProduto);
}