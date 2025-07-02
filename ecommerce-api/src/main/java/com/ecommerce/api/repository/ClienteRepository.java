package com.ecommerce.api.repository;

import com.ecommerce.api.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    Optional<Cliente> findByEmail(String email);
    
    Optional<Cliente> findByCpf(String cpf);
    
    List<Cliente> findByAtivoTrue();
    
    Optional<Cliente> findByIdAndAtivoTrue(Long id);
    
    @Query("SELECT c FROM Cliente c WHERE c.nome LIKE %:nome% AND c.ativo = true")
    List<Cliente> findByNomeContainingAndAtivoTrue(@Param("nome") String nome);
    
    @Query("SELECT c FROM Cliente c WHERE c.email = :email AND c.ativo = true")
    Optional<Cliente> findByEmailAndAtivoTrue(@Param("email") String email);
    
    boolean existsByEmail(String email);
    
    boolean existsByCpf(String cpf);
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cliente c WHERE c.email = :email AND c.id != :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cliente c WHERE c.cpf = :cpf AND c.id != :id")
    boolean existsByCpfAndIdNot(@Param("cpf") String cpf, @Param("id") Long id);
}