package com.ecommerce.api.repository;

import com.ecommerce.api.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
    Optional<Categoria> findByNome(String nome);
    
    @Query("SELECT c FROM Categoria c WHERE c.nome LIKE %:nome%")
    List<Categoria> findByNomeContaining(String nome);
    
    @Query("SELECT c FROM Categoria c ORDER BY c.dataCriacao DESC")
    List<Categoria> findAllOrderByDataCriacaoDesc();
}