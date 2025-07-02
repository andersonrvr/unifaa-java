package com.ecommerce.api.repository;

import com.ecommerce.api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    List<Produto> findByCategoriaId(Long categoriaId);
    
    List<Produto> findByAtivoTrue();
    
    Optional<Produto> findByIdAndAtivoTrue(Long id);
    
    @Query("SELECT p FROM Produto p WHERE p.nome LIKE %:nome% AND p.ativo = true")
    List<Produto> findByNomeContainingAndAtivoTrue(@Param("nome") String nome);
    
    @Query("SELECT p FROM Produto p WHERE p.preco BETWEEN :precoMin AND :precoMax AND p.ativo = true")
    List<Produto> findByPrecoBetweenAndAtivoTrue(@Param("precoMin") BigDecimal precoMin, 
                                                @Param("precoMax") BigDecimal precoMax);
    
    @Query("SELECT p FROM Produto p WHERE p.categoria.id = :categoriaId AND p.ativo = true ORDER BY p.nome")
    List<Produto> findByCategoriaIdAndAtivoTrueOrderByNome(@Param("categoriaId") Long categoriaId);
    
    @Query("SELECT p FROM Produto p WHERE p.quantidadeEstoque < :quantidade AND p.ativo = true")
    List<Produto> findByQuantidadeEstoqueLessThanAndAtivoTrue(@Param("quantidade") Integer quantidade);
}