package com.viniciusgomes.cursomc.resources;

import com.viniciusgomes.cursomc.domain.Categoria;
import com.viniciusgomes.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> find(@PathVariable  Integer id) {
        Categoria obj = service.buscar(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> insert ( @RequestBody Categoria obj) {
        obj = service.inserir(obj); // A operação save do Repository me retorna um objeto
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}") // fromCurrentRequest pega a url usada para inserir
                .buildAndExpand(obj.getId()) //buildAndExpand usado para incluir o id no parâmetro /{id}
                .toUri();
        return ResponseEntity.created(uri).build();
    }

}
