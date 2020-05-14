package com.viniciusgomes.cursomc.services.validation;

import com.viniciusgomes.cursomc.domain.Cliente;
import com.viniciusgomes.cursomc.dto.ClienteDTO;
import com.viniciusgomes.cursomc.repositories.ClienteRepository;
import com.viniciusgomes.cursomc.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//Validator necessário para criar anotação
// Um validator deve implementar a classe ConstraintValidator
// no generics <A, B>, com A sendo a classe que marca o nome da notação e B sendo o tipo da classe que aceitará a notação
public class ClienteUpdateValidator implements ConstraintValidator <ClienteUpdate, ClienteDTO> {

    // Na validação, preciso comparar o id do objDTO que vem na requisição com um objeto que eu busco no
    // banco de dados, mas o id do objDTO vem na uri e não no objeto. Para isso, precisarei usar uma função
    // da classe HttpServletRequest que me permite obter parâmetros da uri. Essa função é o método getAttribute
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteUpdate ann) {
    }


    //isValid() é o método da interface ConstraintValidator responsável por testar se a classe ClienteNewDTO
    // é válida ou não. Ele deve retornar true se a classe for válida e false se ela for inválida
    // o método isValid afeta toda anotação @Valid associada à classe ClienteNewDTO
    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        // Ao ler uma estrutura JSON, preciso armazená-la em um Map, afinal JSON segue uma estrutura
        // chave valor. O método getAttribute retorna os parâmetros do JSON. O casting é necessário porque o
        // retorno de getAttribute é um Object
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        // Depois de ter criado uma collection do tipo Map com os atributos JSON da requisição,
        // posso acessá-las individualmente por meio do método get do Map. No entando, o get retorna uma String
        // e na comparação de ids precisarei de um Integer
        Integer uriId = Integer.parseInt(map.get("id"));


        // A lista do tipo FieldMessage será populada com os resultados de todos os testes
        // feitos em cima dos atributos de ClienteNewDTO
        List<FieldMessage> list = new ArrayList<>();


        //Lógica para testar se o email do cliente que está sendo inserido no BD já existe,
        // garantindo unicidade ao e-mail. Além disso, o objeto que vem do BD tem o id
        // que será necessário na comparação com o id de objDTO
        Cliente aux = repo.findByEmail(objDto.getEmail());

        if (aux != null && !aux.getId().equals(uriId)) {
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