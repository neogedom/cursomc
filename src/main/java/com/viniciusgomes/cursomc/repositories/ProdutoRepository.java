package com.viniciusgomes.cursomc.repositories;

import com.viniciusgomes.cursomc.domain.Categoria;
import com.viniciusgomes.cursomc.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    // Quando precisamos de uma operação no banco de dados que seja diferente daquelas providas pelos Query Methods
    // do Spring Data JPA, precisamos escrever o script JPQL. Uma forma prática de se fazer isso, sem a necessidade
    // de se criar uma classe que implementa essa interface é usando a anotação @Query com o JPQL embutido nela
    // A notação @Param joga o resultado da consulta JPQL incluído nos parâmetros seguidos de : dentro das variáveis correspondentes
   // @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
   // Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);

    //É apenas uma consulta, então não é necessário realizar uma transação no BD
    @Transactional(readOnly = true)
    Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);
}
