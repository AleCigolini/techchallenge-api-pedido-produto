package br.com.fiap.techchallengeapipedidoproduto.produto.application.presenter.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.presenter.CategoriaProdutoPresenter;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.CategoriaProdutoResponseDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import org.modelmapper.ModelMapper;

import java.util.List;

public class CategoriaProdutoModelMapperPresenterImpl implements CategoriaProdutoPresenter {

    private final ModelMapper modelMapper;

    public CategoriaProdutoModelMapperPresenterImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoriaProdutoResponseDTO categoriaProdutoParaCategoriaProdutoResponseDTO(CategoriaProduto categoriaProduto) {
        return modelMapper.map(categoriaProduto, CategoriaProdutoResponseDTO.class);
    }

    @Override
    public List<CategoriaProdutoResponseDTO> categoriasProdutoParaCategoriasProdutoResponseDTO(List<CategoriaProduto> categoriasProduto) {
        return categoriasProduto.stream().map(categoriaProduto -> modelMapper.map(categoriaProduto, CategoriaProdutoResponseDTO.class)).toList();
    }
}
