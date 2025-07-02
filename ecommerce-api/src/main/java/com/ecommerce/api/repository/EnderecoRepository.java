package com.ecommerce.api.repository;

import com.ecommerce.api.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    
    List<Endereco> findByClienteId(Long clienteId);
    
    @Query("SELECT e FROM Endereco e WHERE e.cliente.id = :clienteId ORDER BY e.dataCriacao DESC")
    List<Endereco> findByClienteIdOrderByDataCriacaoDesc(@Param("clienteId") Long clienteId);
    
    @Query("SELECT e FROM Endereco e WHERE e.cep = :cep")
    List<Endereco> findByCep(@Param("cep") String cep);
    
    @Query("SELECT e FROM Endereco e WHERE e.cidade = :cidade AND e.estado = :estado")
    List<Endereco> findByCidadeAndEstado(@Param("cidade") String cidade, @Param("estado") String estado);
    
    @Query("SELECT COUNT(e) FROM Endereco e WHERE e.cliente.id = :clienteId")
    Long countByClienteId(@Param("clienteId") Long clienteId);
}