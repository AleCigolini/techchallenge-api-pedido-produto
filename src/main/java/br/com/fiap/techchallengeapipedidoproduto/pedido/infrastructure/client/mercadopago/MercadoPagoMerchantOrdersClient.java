package br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.client.mercadopago;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "MercadoPagoMerchantOrdersClient", url = "https://api.mercadopago.com/merchant_orders")
public interface MercadoPagoMerchantOrdersClient {

    @GetMapping("/{payment_id}")
    ResponseEntity<Object> obterPagamento(
            @PathVariable("payment_id") String paymentId,
            @RequestHeader("Authorization") String authHeader);
}