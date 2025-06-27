package br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WebhookDataRequestDto {

    @JsonProperty("currency_id")
    private String currencyId;
    private String marketplace;
    private String status;
}