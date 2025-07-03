package br.com.fiap.techchallengeapipedidoproduto.produto.application.controller.impl;

import br.com.fiap.techchallengeapipedidoproduto.produto.application.controller.CategoriaProdutoController;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.controller.ProdutoController;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.ProdutoGateway;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.gateway.impl.ProdutoGatewayImpl;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.ProdutoMapper;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.mapper.impl.ProdutoMapperImpl;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.presenter.ProdutoPresenter;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.presenter.impl.ProdutoModelMapperPresenterImpl;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.AtualizarProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.BuscarProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.ExcluirProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.SalvarProdutoUseCase;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl.AtualizarProdutoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl.BuscarProdutoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl.ExcluirProdutoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.produto.application.usecase.impl.SalvarProdutoUseCaseImpl;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.ProdutoRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.ProdutoResponseDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.CategoriaProduto;
import br.com.fiap.techchallengeapipedidoproduto.produto.domain.Produto;
import br.com.fiap.techchallengeapipedidoproduto.produto.infrastructure.database.adapter.ProdutoDatabase;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoControllerImpl implements ProdutoController {

    private final BuscarProdutoUseCase buscarProdutoUseCase;
    private final SalvarProdutoUseCase salvarProdutoUseCase;
    private final AtualizarProdutoUseCase atualizarProdutoUseCase;
    private final ExcluirProdutoUseCase excluirProdutoUseCase;
    private final ProdutoPresenter produtoPresenter;
    private final ProdutoMapper produtoMapper;
    private final CategoriaProdutoController categoriaProdutoController;

    public ProdutoControllerImpl(ModelMapper modelMapper, ProdutoDatabase produtoDatabase, CategoriaProdutoController categoriaProdutoController) {
        this.produtoPresenter = new ProdutoModelMapperPresenterImpl(modelMapper);
        this.produtoMapper = new ProdutoMapperImpl(modelMapper);
        final ProdutoGateway produtoGateway = new ProdutoGatewayImpl(produtoDatabase, produtoMapper);
        this.buscarProdutoUseCase = new BuscarProdutoUseCaseImpl(produtoGateway);
        this.salvarProdutoUseCase = new SalvarProdutoUseCaseImpl(produtoGateway);
        this.atualizarProdutoUseCase = new AtualizarProdutoUseCaseImpl(produtoGateway);
        this.excluirProdutoUseCase = new ExcluirProdutoUseCaseImpl(produtoGateway);
        this.categoriaProdutoController = categoriaProdutoController;
    }

    public List<ProdutoResponseDTO> buscarProdutos() {
        List<Produto> produtos = buscarProdutoUseCase.buscarProdutos();

        return produtoPresenter.produtosParaProdutosResponseDTO(produtos);
    }

    public List<ProdutoResponseDTO> buscarProdutosPorCategoria(String idCategoriaProduto) {
        List<Produto> produtos = buscarProdutoUseCase.buscarProdutosPorCategoria(idCategoriaProduto);

        List<ProdutoResponseDTO> produtosResponseDTOs = produtoPresenter.produtosParaProdutosResponseDTO(produtos);

        produtosResponseDTOs.forEach(produtoResponseDTO -> {
            CategoriaProduto categoriaProduto = categoriaProdutoController.buscarCategoriaProdutoPorId(produtoResponseDTO.getCategoriaProduto().getId());
            produtoResponseDTO.setCategoriaProduto(categoriaProduto);
        });

        return produtosResponseDTOs;
    }

    public ProdutoResponseDTO buscarProdutoPorId(String id) {
        Produto produto = buscarProdutoUseCase.buscarProdutoPorId(id);

        return produtoPresenter.produtoParaProdutoResponseDTO(produto);
    }

    public ProdutoResponseDTO criarProduto(ProdutoRequestDTO produtoRequestDTO) {
        Produto produto = produtoMapper.produtoRequestDtoParaProduto(produtoRequestDTO);

        Produto produtoCriado = salvarProdutoUseCase.criarProduto(produto);
        CategoriaProduto categoriaProduto = categoriaProdutoController.buscarCategoriaProdutoPorId(produtoRequestDTO.getIdCategoria());

        ProdutoResponseDTO produtoResponseDTO = produtoPresenter.produtoParaProdutoResponseDTO(produtoCriado);
        produtoResponseDTO.setCategoriaProduto(categoriaProduto);

        return produtoResponseDTO;
    }

    public ProdutoResponseDTO atualizarProduto(ProdutoRequestDTO produtoRequestDTO, String id) {
        buscarProdutoUseCase.buscarProdutoPorId(id);

        Produto produto = produtoMapper.produtoRequestDtoParaProduto(produtoRequestDTO);
        produto.setId(id);

        Produto produtoAtualizado = atualizarProdutoUseCase.atualizarProduto(produto);
        CategoriaProduto categoriaProduto = categoriaProdutoController.buscarCategoriaProdutoPorId(produtoRequestDTO.getIdCategoria());

        ProdutoResponseDTO produtoResponseDTO = produtoPresenter.produtoParaProdutoResponseDTO(produtoAtualizado);
        produtoResponseDTO.setCategoriaProduto(categoriaProduto);

        return produtoResponseDTO;
    }

    public void excluirProduto(String idProduto) {
        excluirProdutoUseCase.excluirProduto(idProduto);
    }
}
