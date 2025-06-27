package br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adpater.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaCategoriaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adpater.CategoriaProdutoDatabase;
import br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.repository.JpaCategoriaProdutoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class CategoriaProdutoJpaDatabaseImpl implements CategoriaProdutoDatabase {

    private final JpaCategoriaProdutoRepository jpaCategoriaProdutoRepository;

    public CategoriaProdutoJpaDatabaseImpl(JpaCategoriaProdutoRepository jpaCategoriaProdutoRepository) {
        this.jpaCategoriaProdutoRepository = jpaCategoriaProdutoRepository;
    }

    @Override
    public JpaCategoriaProdutoEntity save(JpaCategoriaProdutoEntity jpaCategoriaProdutoEntity) {
        return jpaCategoriaProdutoRepository.save(jpaCategoriaProdutoEntity);
    }

    @Override
    public Optional<JpaCategoriaProdutoEntity> findById(UUID uuid) {
        return jpaCategoriaProdutoRepository.findById(uuid);
    }

    @Override
    public List<JpaCategoriaProdutoEntity> findAllByAtivoTrue() {
        return jpaCategoriaProdutoRepository.findAllByAtivoTrue();
    }
}
