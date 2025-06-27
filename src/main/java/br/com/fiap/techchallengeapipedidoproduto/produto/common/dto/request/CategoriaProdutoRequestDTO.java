package br.com.fiap.techchallengeapipedidoproduto.produto.common.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Representação da requisição para cadastrar uma categoria de produto")
public class CategoriaProdutoRequestDTO {

    @Schema(description = "Nome do acompanhamento", example = "Nome do Acompanhamento", maxLength = 40)
    private String nome;

}