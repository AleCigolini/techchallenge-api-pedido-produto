package br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.database.adapter;

import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.entity.JpaPedidoEntity;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.domain.entity.JpaProdutoPedidoEntity;
import br.com.fiap.techchallengeapipedidoproduto.pedido.common.interfaces.PedidoDatabase;
import br.com.fiap.techchallengeapipedidoproduto.pedido.domain.StatusPedidoEnum;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.database.repository.JpaPedidoRepository;
import br.com.fiap.techchallengeapipedidoproduto.pedido.infrastructure.database.repository.JpaProdutoPedidoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PedidoJpaDatabaseImpl implements PedidoDatabase {

    private final JpaPedidoRepository jpaPedidoRepository;
    private final JpaProdutoPedidoRepository jpaProdutoPedidoRepository;

    public PedidoJpaDatabaseImpl(JpaPedidoRepository jpaPedidoRepository,
                                 JpaProdutoPedidoRepository jpaProdutoPedidoRepository) {
        this.jpaPedidoRepository = jpaPedidoRepository;
        this.jpaProdutoPedidoRepository = jpaProdutoPedidoRepository;
    }

    @Override
    public List<JpaPedidoEntity> buscarPedidos(List<StatusPedidoEnum> statusPedidoEnums) {

        if (statusPedidoEnums == null || statusPedidoEnums.isEmpty()) {
            return jpaPedidoRepository.findAll();

        } else {
            List<String> status = statusPedidoEnums.stream().map(StatusPedidoEnum::toString).toList();
            return jpaPedidoRepository.findAllByStatusIn(status);
        }
    }

    @Override
    public JpaPedidoEntity criarPedido(JpaPedidoEntity jpaPedidoEntity) {
        JpaPedidoEntity jpaPedidoEntitySalvo = jpaPedidoRepository.save(jpaPedidoEntity);

        for (JpaProdutoPedidoEntity jpaProdutoPedidoEntity : jpaPedidoEntity.getProdutos()) {
            jpaProdutoPedidoEntity.setPedido(jpaPedidoEntitySalvo);
            jpaProdutoPedidoRepository.save(jpaProdutoPedidoEntity);
        }
        return jpaPedidoEntitySalvo;
    }

    @Override
    public JpaPedidoEntity salvarPedido(JpaPedidoEntity jpaPedidoEntity) {
        return jpaPedidoRepository.save(jpaPedidoEntity);
    }

    @Override
    public Optional<JpaPedidoEntity> buscarPedidoPorId(String id) {
        return jpaPedidoRepository.findById(UUID.fromString(id));
    }
}