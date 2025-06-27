package br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adpater.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaCategoriaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adpater.ProdutoDatabase;
import br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.repository.JpaProdutoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProdutoJpaDatabaseImpl implements ProdutoDatabase {

    private final JpaProdutoRepository jpaProdutoRepository;

    public ProdutoJpaDatabaseImpl(JpaProdutoRepository jpaProdutoRepository) {
        this.jpaProdutoRepository = jpaProdutoRepository;
    }

    @Override
    public List<JpaProdutoEntity> findAllByOrderByNomeAsc() {
        return jpaProdutoRepository.findAllByOrderByNomeAsc();
    }

    @Override
    public List<JpaProdutoEntity> findAllByCategoriaOrderByNomeAsc(JpaCategoriaProdutoEntity jpaCategoriaProdutoEntity) {
        return jpaProdutoRepository.findAllByCategoriaOrderByNomeAsc(jpaCategoriaProdutoEntity);
    }

    @Override
    public Optional<JpaProdutoEntity> findById(UUID uuid) {
        return jpaProdutoRepository.findById(uuid);
    }

    @Override
    public JpaProdutoEntity save(JpaProdutoEntity jpaProdutoEntity) {
        return jpaProdutoRepository.save(jpaProdutoEntity);
    }

    @Override
    public void deleteById(UUID uuid) {
        jpaProdutoRepository.deleteById(uuid);
    }
}
