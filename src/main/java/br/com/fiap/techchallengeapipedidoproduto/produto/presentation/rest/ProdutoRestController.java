package br.com.fiap.techchallengeapipedidoproduto.produto.presentation.rest;

import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request.ProdutoRequestDTO;
import br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response.ProdutoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.List;

@Tag(name = "${tag.swagger.produto.name}", description = "${tag.swagger.produto.description}")
public interface ProdutoRestController {

    /**
     * Busca todos os produtos
     *
     * @return {@link ProdutoResponseDTO}
     */
    @Operation(summary = "Buscar todos os produtos", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(ref = "Problema"))
            )
    })
    ResponseEntity<List<ProdutoResponseDTO>> buscarProdutos();

    /**
     * Busca os produtos por categoria
     *
     * @param idCategoriaProduto Long da categoria produto
     * @return {@link ProdutoResponseDTO}
     */
    @Operation(summary = "Buscar os produtos por categoria", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(ref = "Problema"))
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(ref = "Problema"))
            )
    })
    ResponseEntity<List<ProdutoResponseDTO>> buscarProdutosPorCategoria(String idCategoriaProduto);

    /**
     * Busca produto por ID
     *
     * @param id Long do produto
     * @return {@link ProdutoResponseDTO}
     */
    @Operation(summary = "Buscar produto por ID", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(ref = "Problema"))
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(ref = "Problema"))
            )
    })
    ResponseEntity<ProdutoResponseDTO> buscarProdutoPorId(String id);

    /**
     * Criar produto
     *
     * @param produtoRequestDTO DTO para criação de produto
     * @return {@link ProdutoResponseDTO}
     */
    @Operation(summary = "Criar novo produto", responses = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(ref = "Problema"))
            )
    })
    ResponseEntity<ProdutoResponseDTO> criarProduto(ProdutoRequestDTO produtoRequestDTO) throws URISyntaxException;

    /**
     * Atualizar produto
     *
     * @param produtoRequestDTO DTO para atualização de produto
     * @param id ID do produto a ser atualizado
     * @return {@link ProdutoResponseDTO}
     */
    @Operation(summary = "Atualizar um produto", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content(schema = @Schema(ref = "Problema"))
            ),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(ref = "Problema"))
            )
    })
    ResponseEntity<ProdutoResponseDTO> atualizarProduto(ProdutoRequestDTO produtoRequestDTO, String id);

    /**
     * Excluir um produto
     *
     * @param id ID do produto a ser excluído
     * @return void
     */
    @Operation(summary = "Excluir um produto", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",
                    content = @Content(schema = @Schema(ref = "Problema"))
            )
    })
    ResponseEntity<Void> excluirProduto(String id);
}
