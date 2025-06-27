package br.com.fiap.techchallengeapipedidoproduto.produto.application.presenter;

import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.ProdutoResponseDTO;

import java.util.List;

public interface ProdutoPresenter {

    List<ProdutoResponseDTO> produtosParaProdutosResponseDTO(List<Produto> produtos);

    ProdutoResponseDTO produtoParaProdutoResponseDTO(Produto produto);
}
