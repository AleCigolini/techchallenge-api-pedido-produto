package br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.CategoriaProdutoMapper;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.CategoriaProdutoRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaCategoriaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CategoriaProdutoMapperImpl implements CategoriaProdutoMapper {

    private final ModelMapper modelMapper;

    public CategoriaProdutoMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoriaProduto categoriaProdutoRequestDTOParaCategoriaProduto(CategoriaProdutoRequestDTO categoriaProdutoRequestDTO) {
        return modelMapper.map(categoriaProdutoRequestDTO, CategoriaProduto.class);
    }

    @Override
    public JpaCategoriaProdutoEntity categoriaProdutoParaJpaCategoriaProdutoEntity(CategoriaProduto categoriaProduto) {
        return modelMapper.map(categoriaProduto, JpaCategoriaProdutoEntity.class);
    }

    @Override
    public CategoriaProduto jpaCategoriaProdutoEntityParaCategoriaProduto(JpaCategoriaProdutoEntity jpaCategoriaProdutoEntity) {
        return modelMapper.map(jpaCategoriaProdutoEntity, CategoriaProduto.class);
    }
}
