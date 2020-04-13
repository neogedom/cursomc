package com.viniciusgomes.cursomc.resources;

import com.viniciusgomes.cursomc.domain.Categoria;
import com.viniciusgomes.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;

    @GetMapping(value = "{id}")
    public ResponseEntity<?> find(@PathVariable  Integer id) {
        Categoria obj = service.buscar(id);
        return ResponseEntity.ok().body(obj);
    }


}
