package com.viniciusgomes.cursomc.services;

import com.viniciusgomes.cursomc.domain.Categoria;
import com.viniciusgomes.cursomc.domain.Cliente;
import com.viniciusgomes.cursomc.dto.CategoriaDTO;
import com.viniciusgomes.cursomc.repositories.CategoriaRepository;
import com.viniciusgomes.cursomc.services.exceptions.DataIntegrityException;
import com.viniciusgomes.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Categoria newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete (Integer id) {
        try {
            find(id);
            repo.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
        }

        repo.deleteById(id);
    }

    public List<Categoria> findAll() {
        return repo.findAll();
    }

    // page = parâmetro que contém a página desejada
    // linesPerPage = quantas linhas desejadas por página
    // orderBy = informa por qual parâmetro quero ordenar
    // direction = informar qual a direção desejada da ordenação (ascendente ou descendente)
    public Page<Categoria> findPage (Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Categoria fromDTO(CategoriaDTO objDTO) {
        return new Categoria(objDTO.getId(), objDTO.getNome());
    }

    private void updateData (Categoria newObj, Categoria obj) {
        newObj.setNome(obj.getNome());
    }
}
