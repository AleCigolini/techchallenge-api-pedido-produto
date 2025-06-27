package br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.ProdutoMapper;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.ProdutoRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.entity.JpaProdutoEntity;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProdutoMapperImpl implements ProdutoMapper {
    private final ModelMapper modelMapper;

    @Override
    public Produto produtoRequestDtoParaProduto(ProdutoRequestDTO produtoRequestDTO) {
        modelMapper.typeMap(ProdutoRequestDTO.class, Produto.class).addMappings(mapper -> mapper.skip(Produto::setId));

        return modelMapper.map(produtoRequestDTO, Produto.class);
    }

    @Override
    public Produto jpaProdutoEntityParaProduto(JpaProdutoEntity jpaProdutoEntity) {
        return modelMapper.map(jpaProdutoEntity, Produto.class);
    }

    @Override
    public JpaProdutoEntity produtoParaJpaProdutoEntity(Produto produto) {
        return modelMapper.map(produto, JpaProdutoEntity.class);
    }
}
