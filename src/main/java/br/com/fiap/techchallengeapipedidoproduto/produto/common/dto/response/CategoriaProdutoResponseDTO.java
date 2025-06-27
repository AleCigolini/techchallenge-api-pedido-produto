package br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Schema(description = "Representação da resposta de uma categoria de produto")
public class CategoriaProdutoResponseDTO {

    @Schema(description = "Identificador único da categoria de produto", example = "1")
    private String id;

    @Schema(description = "Nome da categoria de produto", example = "Nome da Categoria de Produto", maxLength = 40)
    private String nome;

    @Schema(description = "Data de criação da categoria de produto", example = "2023-01-01T10:00:00Z")
    private OffsetDateTime dataCriacao;

    @Schema(description = "Data de atualização da categoria de produto", example = "2023-01-02T10:00:00Z")
    private OffsetDateTime dataAtualizacao;
}