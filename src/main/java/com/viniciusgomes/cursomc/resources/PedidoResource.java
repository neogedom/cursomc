package com.viniciusgomes.cursomc.resources;

import com.viniciusgomes.cursomc.domain.Categoria;
import com.viniciusgomes.cursomc.domain.Pedido;
import com.viniciusgomes.cursomc.dto.CategoriaDTO;
import com.viniciusgomes.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Pedido> find(@PathVariable  Integer id) {
        Pedido obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> insert (@Valid @RequestBody Pedido obj) {
        obj = service.insert(obj); // A operação save do Repository me retorna um objeto
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}") // fromCurrentRequest pega a url usada para inserir
                .buildAndExpand(obj.getId()) //buildAndExpand usado para incluir o id no parâmetro /{id}
                .toUri();
        return ResponseEntity.created(uri).build();
    }


}
