package br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adpater;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaCategoriaProdutoEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoriaProdutoDatabase {

    JpaCategoriaProdutoEntity save(JpaCategoriaProdutoEntity jpaCategoriaProdutoEntity);

    Optional<JpaCategoriaProdutoEntity> findById(UUID uuid);

    List<JpaCategoriaProdutoEntity> findAllByAtivoTrue();
}
