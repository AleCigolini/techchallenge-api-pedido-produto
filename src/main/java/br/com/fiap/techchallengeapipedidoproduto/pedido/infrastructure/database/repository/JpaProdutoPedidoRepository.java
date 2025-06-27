package br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.database.repository;

import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.entity.JpaProdutoPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProdutoPedidoRepository extends JpaRepository<JpaProdutoPedidoEntity, Long> {

}