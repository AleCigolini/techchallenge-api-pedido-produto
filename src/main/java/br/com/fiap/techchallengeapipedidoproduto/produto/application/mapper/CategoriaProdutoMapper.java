package br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaCategoriaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.CategoriaProdutoRequestDTO;

public interface CategoriaProdutoMapper {

    CategoriaProduto categoriaProdutoRequestDTOParaCategoriaProduto(CategoriaProdutoRequestDTO categoriaProdutoRequestDTO);
    JpaCategoriaProdutoEntity categoriaProdutoParaJpaCategoriaProdutoEntity(CategoriaProduto categoriaProduto);
    CategoriaProduto jpaCategoriaProdutoEntityParaCategoriaProduto(JpaCategoriaProdutoEntity jpaCategoriaProdutoEntity);
}
