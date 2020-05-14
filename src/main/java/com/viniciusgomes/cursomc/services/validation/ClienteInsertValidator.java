package com.viniciusgomes.cursomc.services.validation;

import com.viniciusgomes.cursomc.domain.Cliente;
import com.viniciusgomes.cursomc.domain.enums.TipoCliente;
import com.viniciusgomes.cursomc.dto.ClienteNewDTO;
import com.viniciusgomes.cursomc.repositories.ClienteRepository;
import com.viniciusgomes.cursomc.resources.exceptions.FieldMessage;
import com.viniciusgomes.cursomc.services.validation.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

//Validator necessário para criar anotação
// Um validator deve implementar a classe ConstraintValidator
// no generics <A, B>, com A sendo a classe que marca o nome da notação e B sendo o tipo da classe que aceitará a notação
public class ClienteInsertValidator implements ConstraintValidator <ClienteInsert, ClienteNewDTO> {

    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert ann) {
    }


    //isValid() é o método da interface ConstraintValidator responsável por testar se a classe ClienteNewDTO
    // é válida ou não. Ele deve retornar true se a classe for válida e false se ela for inválida
    // o método isValid afeta toda anotação @Valid associada à classe ClienteNewDTO
    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

        // A lista do tipo FieldMessage será populada com os resultados de todos os testes
        // feitos em cima dos atributos de ClienteNewDTO
        List<FieldMessage> list = new ArrayList<>();

        // Testes de validação customizada
        if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }
        if(objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

        //Lógica para testar se o email do cliente que está sendo inserido no BD já existe,
        // garantindo unicidade ao e-mail
        Cliente aux = repo.findByEmail(objDto.getEmail());
        if (aux != null) {
            list.add(new FieldMessage("email","E-mail já existente"));
        }

        // É necessário transportar a lista de erros criadas para a lista de erros do framework
        // para que na captura da exceção, o framework consiga lidar com a lista e mandá-la de volta
        // na resposta da requisição
        for (FieldMessage e : list) { //Para cada erro existente na lista de FieldMessages
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation(); // adicionar à lista de erros do framework sua mensagem
                                                                                // e seu fieldName
        }

        //Se a lista estiver vazia, não houve nenhum erro. Portanto, o método retornará verdadeiro (isEmpty == true)
        //Se houver algum erro, a lista não estará vazia. Portanto, o método retornará falso (isEmpty == false)
        return list.isEmpty();
    }
}