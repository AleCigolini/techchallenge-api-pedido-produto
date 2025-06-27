package br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.ProdutoRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;

public interface ProdutoMapper {

    Produto produtoRequestDtoParaProduto(ProdutoRequestDTO produtoRequestDTO);

    Produto jpaProdutoEntityParaProduto(JpaProdutoEntity jpaProdutoEntity);

    JpaProdutoEntity produtoParaJpaProdutoEntity(Produto produto);
}
