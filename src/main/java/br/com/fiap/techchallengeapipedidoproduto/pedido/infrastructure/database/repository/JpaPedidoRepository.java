package br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.database.repository;

import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.entity.JpaPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JpaPedidoRepository extends JpaRepository<JpaPedidoEntity, UUID> {

    @Query(value = "SELECT e FROM JpaPedidoEntity e " +
            "WHERE e.status IN :status " +
            "ORDER BY CASE e.status " +
            "WHEN 'PRONTO' THEN 1 " +
            "WHEN 'EM_PREPARACAO' THEN 2 " +
            "WHEN 'RECEBIDO' THEN 3 " +
            "ELSE 4 END ASC, e.dataCriacao ASC")
    List<JpaPedidoEntity> findAllByStatusIn(@Param("status") List<String> status);
}
