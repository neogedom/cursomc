package com.viniciusgomes.cursomc.repositories;

import com.viniciusgomes.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    //Com o parâmetro readOnly informo ao programa que a transação não precisa ser envolvida
    // com uma transação com BD, deixando o sistema mais rápido e diminuindo o locking
    @Transactional(readOnly = true)
    //Usando padrão de nomes para criar métodos, o Spring Data implementa o método automaticamente
    Cliente findByEmail(String email);

}
