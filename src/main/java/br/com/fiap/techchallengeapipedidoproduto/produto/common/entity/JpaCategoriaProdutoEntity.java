package br.com.fiap.techchallengeapipedidoproduto.produto.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "categoria_produto")
public class JpaCategoriaProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    @Column(name = "data_atualizacao", nullable = false)
    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;

    @Column(name = "e_ativo", nullable = false)
    private Boolean ativo = Boolean.TRUE;
}