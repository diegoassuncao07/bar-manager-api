package com.diegoassuncao.barmanager.repository;

import com.diegoassuncao.barmanager.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCase(String nome);
}
