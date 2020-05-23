package com.viniciusgomes.cursomc.services;

import com.viniciusgomes.cursomc.domain.*;
import com.viniciusgomes.cursomc.domain.enums.EstadoPagamento;
import com.viniciusgomes.cursomc.repositories.ItemPedidoRepository;
import com.viniciusgomes.cursomc.repositories.PagamentoRepository;
import com.viniciusgomes.cursomc.repositories.PedidoRepository;
import com.viniciusgomes.cursomc.repositories.ProdutoRepository;
import com.viniciusgomes.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;
    @Autowired
    private BoletoService boletoService;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Pedido find(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException
                ("Objeto não encontrado! Id " + id + " , Tipo: " + Pedido.class.getName()));
    }

    public Pedido insert (Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }

        obj = repo.save(obj);
        pagamentoRepository.save(obj.getPagamento());

        for(ItemPedido item : obj.getItens()) {
            item.setDesconto(0.0);
            item.setPreco( produtoRepository.findById(item.getProduto().getId()).get().getPreco());
            item.setPedido(obj);
        }

        itemPedidoRepository.saveAll(obj.getItens());
        return obj;

    }

}
