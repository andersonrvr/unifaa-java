package com.ecommerce.api.controller;

import com.ecommerce.api.dto.EnderecoDTO;
import com.ecommerce.api.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/enderecos")
@Tag(name = "Endereços", description = "Operações relacionadas aos endereços")
public class EnderecoController {
    
    @Autowired
    private EnderecoService enderecoService;
    
    @GetMapping
    @Operation(summary = "Listar todos os endereços")
    public ResponseEntity<List<EnderecoDTO>> findAll() {
        List<EnderecoDTO> enderecos = enderecoService.findAll();
        return ResponseEntity.ok(enderecos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar endereço por ID")
    public ResponseEntity<EnderecoDTO> findById(@PathVariable Long id) {
        EnderecoDTO endereco = enderecoService.findById(id);
        return ResponseEntity.ok(endereco);
    }
    
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Listar endereços por cliente")
    public ResponseEntity<List<EnderecoDTO>> findByClienteId(@PathVariable Long clienteId) {
        List<EnderecoDTO> enderecos = enderecoService.findByClienteId(clienteId);
        return ResponseEntity.ok(enderecos);
    }
    
    @PostMapping
    @Operation(summary = "Criar novo endereço")
    public ResponseEntity<EnderecoDTO> save(@Valid @RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO novoEndereco = enderecoService.save(enderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEndereco);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar endereço")
    public ResponseEntity<EnderecoDTO> update(@PathVariable Long id, @Valid @RequestBody EnderecoDTO enderecoDTO) {
        EnderecoDTO enderecoAtualizado = enderecoService.update(id, enderecoDTO);
        return ResponseEntity.ok(enderecoAtualizado);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar endereço")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        enderecoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/cep/{cep}")
    @Operation(summary = "Buscar endereços por CEP")
    public ResponseEntity<List<EnderecoDTO>> findByCep(@PathVariable String cep) {
        List<EnderecoDTO> enderecos = enderecoService.findByCep(cep);
        return ResponseEntity.ok(enderecos);
    }
    
    @GetMapping("/localidade")
    @Operation(summary = "Buscar endereços por cidade e estado")
    public ResponseEntity<List<EnderecoDTO>> findByCidadeAndEstado(
            @RequestParam String cidade, 
            @RequestParam String estado) {
        List<EnderecoDTO> enderecos = enderecoService.findByCidadeAndEstado(cidade, estado);
        return ResponseEntity.ok(enderecos);
    }
    
    @GetMapping("/cliente/{clienteId}/count")
    @Operation(summary = "Contar endereços por cliente")
    public ResponseEntity<Long> countByClienteId(@PathVariable Long clienteId) {
        Long count = enderecoService.countByClienteId(clienteId);
        return ResponseEntity.ok(count);
    }
}