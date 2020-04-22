package com.viniciusgomes.cursomc.services;

import com.viniciusgomes.cursomc.domain.Pedido;
import com.viniciusgomes.cursomc.repositories.PedidoRepository;
import com.viniciusgomes.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public Pedido buscar(Integer id) {
        Optional<Pedido> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException
                ("Objeto não encontrado! Id " + id + " , Tipo: " + Pedido.class.getName()));
    }
}
