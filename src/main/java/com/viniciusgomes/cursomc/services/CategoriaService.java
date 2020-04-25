package com.viniciusgomes.cursomc.services;

import com.viniciusgomes.cursomc.domain.Categoria;
import com.viniciusgomes.cursomc.repositories.CategoriaRepository;
import com.viniciusgomes.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria find(Integer id) {
        Optional<Categoria> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException
                ("Objeto não encontrado! Id " + id + " , Tipo: " + Categoria.class.getName()));
    }

    public Categoria insert (Categoria obj) {
        obj.setId(null); // se o id tiver algum valor, o método save considera que a operação é atualização
        return repo.save(obj);
    }

    public Categoria update (Categoria obj) {
        find(obj.getId());
        return repo.save(obj);
    }
}
