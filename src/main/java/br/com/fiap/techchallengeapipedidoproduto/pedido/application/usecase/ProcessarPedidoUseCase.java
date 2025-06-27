package br.com.fiap.techchallengeapipedidoproduto.pedido.application.usecase;


import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.dto.request.WebhookNotificationRequestDto;

public interface ProcessarPedidoUseCase {

    void processarNotificacao(WebhookNotificationRequestDto notificacao);
}
