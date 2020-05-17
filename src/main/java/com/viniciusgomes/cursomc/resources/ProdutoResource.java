package com.viniciusgomes.cursomc.resources;

import com.viniciusgomes.cursomc.domain.Produto;
import com.viniciusgomes.cursomc.dto.ProdutoDTO;
import com.viniciusgomes.cursomc.resources.utils.URL;
import com.viniciusgomes.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> find(@PathVariable  Integer id) {
        Produto obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    //Embora o método HTTP aqui seja o GET (porque é padrão do REST realizar consultas com o método GET)
    // precisamos da lista de categorias como parâmetro a ser enviado. Nesse caso, a lista de categoria
    // deverá ser passar pela url usando o ? para separar o domínio dos parâmetros
    // Para que isso funcione, é necessário incluir a anotação de parâmetros de URL @RequestParam na assinatura do método
    // passando como valor o nome do parâmetro passado pela URL (nesse caso, nome e categorias)
    @GetMapping
    public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(value = "nome", defaultValue = "") String nome,
                                                     @RequestParam(value = "categorias", defaultValue = "") String categorias,
                                                     @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                     @RequestParam(value = "linesPerPage", defaultValue = "24")  Integer linesPerPage,
                                                     @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
                                                     @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        // O método abaixo converte a lista de categorias no formato String vindo da url
        // e devolve um lista de inteiros, que servirá para ser inserida no método seach
        List<Integer> ids = URL.decodeIntList(categorias);

        // Para o nome, é importante assumir que talvez o usuário digite espaço na String
        // por isso, é necessário criar um método que lide com o encode automático feito pelo Javascript
        // no front end
        String nomeDecoded = URL.decodeParam(nome);

        Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
        Page<ProdutoDTO> listDTO = list
                .map(obj -> new ProdutoDTO(obj)); // Dando um new ProdutoDTO passando cada produto da lista como argumento

        return ResponseEntity.ok().body(listDTO);
    }


}
