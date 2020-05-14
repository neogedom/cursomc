package com.viniciusgomes.cursomc.dto;

import com.viniciusgomes.cursomc.domain.Cliente;
import com.viniciusgomes.cursomc.services.validation.ClienteUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


// @ClienteUpdate é uma anotação personalizada criada para fazer validações customizadas que
// o Spring JPA não trata
@ClienteUpdate
// Não incluir CPF e CNPJ no DTO, pq eles não são nunca alterados
public class ClienteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Length(min = 5, max = 120, message = "O tamanho deve ser entre 5 e 80 caracteres")
    private String nome;

    @NotEmpty(message = "Preenchimento obrigatório")
    @Email(message = "Email inválido")
    private String email;

    // Na busca de um cliente não é permitido retornar seu CPF ou CNPJ e não é necessário retornar seu
    // tipo, por isso essas informações não fazem parte do DTO

    public ClienteDTO() {

    };

    public ClienteDTO (Cliente obj) {
        id = obj.getId();
        nome = obj.getNome();
        email = obj.getEmail();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
