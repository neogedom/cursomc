package com.viniciusgomes.cursomc.resources;


import com.viniciusgomes.cursomc.domain.Cliente;
import com.viniciusgomes.cursomc.dto.ClienteDTO;
import com.viniciusgomes.cursomc.dto.ClienteNewDTO;
import com.viniciusgomes.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {

        return ResponseEntity.ok().body(service.find(id));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Void> insert (@Valid @RequestBody ClienteNewDTO objDto) {
        Cliente obj = service.fromDTO(objDto);
        obj = service.insert(obj); // A operação save do Repository me retorna um objeto
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}") // fromCurrentRequest pega a url usada para inserir
                .buildAndExpand(obj.getId()) //buildAndExpand usado para incluir o id no parâmetro /{id}
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update (@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
        Cliente obj = service.fromDTO(objDTO);
        obj.setId(id);
        obj = service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<Cliente> list = service.findAll();
        List<ClienteDTO> listDTO = list.stream()
                .map(ClienteDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(listDTO);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                       @RequestParam(value = "linesPerPage", defaultValue = "24")  Integer linesPerPage,
                                                       @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
                                                       @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
        Page<ClienteDTO> listDTO = list
                .map(ClienteDTO::new); // Como o Page já é Java 8 compliant, então não é necessário o stream()
                                        // nem o collect()

        return ResponseEntity.ok().body(listDTO);
    }

}
