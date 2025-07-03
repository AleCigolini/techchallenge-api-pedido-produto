package br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adapter;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaCategoriaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaProdutoEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoDatabase {
    List<JpaProdutoEntity> findAllByOrderByNomeAsc();
    List<JpaProdutoEntity> findAllByCategoriaOrderByNomeAsc(JpaCategoriaProdutoEntity jpaCategoriaProdutoEntity);
    Optional<JpaProdutoEntity> findById(UUID uuid);
    JpaProdutoEntity save(JpaProdutoEntity jpaProdutoEntity);
    void deleteById(UUID uuid);

}
