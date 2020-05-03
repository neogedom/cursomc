package com.viniciusgomes.cursomc.services;

import com.viniciusgomes.cursomc.domain.*;
import com.viniciusgomes.cursomc.domain.Cliente;
import com.viniciusgomes.cursomc.domain.enums.TipoCliente;
import com.viniciusgomes.cursomc.dto.ClienteDTO;
import com.viniciusgomes.cursomc.dto.ClienteNewDTO;
import com.viniciusgomes.cursomc.repositories.ClienteRepository;
import com.viniciusgomes.cursomc.repositories.EnderecoRepository;
import com.viniciusgomes.cursomc.services.exceptions.DataIntegrityException;
import com.viniciusgomes.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Cliente find(Integer id) {
        Optional<Cliente> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException
                ("Objeto não encontrado! Id " + id + " , Tipo: " + Cliente.class.getName()));
    }


    public Cliente insert (Cliente obj) {
        obj.setId(null); // se o id tiver algum valor, o método save considera que a operação é atualização
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }


    public Cliente update (Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    public void delete (Integer id) {
        try {
            find(id);
            repo.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
        }

        repo.deleteById(id);
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    // page = parâmetro que contém a página desejada
    // linesPerPage = quantas linhas desejadas por página
    // orderBy = informa por qual parâmetro quero ordenar
    // direction = informar qual a direção desejada da ordenação (ascendente ou descendente)
    public Page<Cliente> findPage (Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Cliente fromDTO(ClienteDTO objDTO) {

        return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
    }

    //DTO usado especificamente para a inserção de novos clientes (POST)
    public Cliente fromDTO(ClienteNewDTO objDTO) {

        Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfOuCnpj(), TipoCliente.toEnum(objDTO.getTipo()));
        Cidade cidade = new Cidade(objDTO.getCidadeId(), null, null);
        Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cidade);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDTO.getTelefone1());

        if (objDTO.getTelefone2() != null) {
            cli.getTelefones().add(objDTO.getTelefone2());
        }

        if (objDTO.getTelefone3() != null) {
            cli.getTelefones().add(objDTO.getTelefone3());
        }

        return cli;
    }


    // Usado para atualizar o objeto que vem pelo endpoint no formato DTO, sem CPF
    // Busca o objeto no banco de dados e devolve esse objeto, pois ele tem o CPF
    private void updateData (Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }
}
