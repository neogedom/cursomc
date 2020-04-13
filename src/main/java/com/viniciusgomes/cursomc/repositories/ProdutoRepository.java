package com.viniciusgomes.cursomc.repositories;

import com.viniciusgomes.cursomc.domain.Categoria;
import com.viniciusgomes.cursomc.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
