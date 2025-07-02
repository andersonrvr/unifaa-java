package com.ecommerce.api.controller;

import com.ecommerce.api.dto.ProdutoDTO;
import com.ecommerce.api.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Operações relacionadas aos produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;
    
    @GetMapping
    @Operation(summary = "Listar todos os produtos")
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        List<ProdutoDTO> produtos = produtoService.findAll();
        return ResponseEntity.ok(produtos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id) {
        ProdutoDTO produto = produtoService.findById(id);
        return ResponseEntity.ok(produto);
    }
    
    @GetMapping("/categoria/{categoriaId}")
    @Operation(summary = "Listar produtos por categoria")
    public ResponseEntity<List<ProdutoDTO>> findByCategoriaId(@PathVariable Long categoriaId) {
        List<ProdutoDTO> produtos = produtoService.findByCategoriaId(categoriaId);
        return ResponseEntity.ok(produtos);
    }
    
    @PostMapping
    @Operation(summary = "Criar novo produto")
    public ResponseEntity<ProdutoDTO> save(@Valid @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO novoProduto = produtoService.save(produtoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto")
    public ResponseEntity<ProdutoDTO> update(@PathVariable Long id, @Valid @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoAtualizado = produtoService.update(id, produtoDTO);
        return ResponseEntity.ok(produtoAtualizado);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar produto")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/buscar")
    @Operation(summary = "Buscar produtos por nome")
    public ResponseEntity<List<ProdutoDTO>> findByNomeContaining(@RequestParam String nome) {
        List<ProdutoDTO> produtos = produtoService.findByNomeContaining(nome);
        return ResponseEntity.ok(produtos);
    }
    
    @GetMapping("/preco")
    @Operation(summary = "Buscar produtos por faixa de preço")
    public ResponseEntity<List<ProdutoDTO>> findByPrecoBetween(
            @RequestParam BigDecimal precoMin, 
            @RequestParam BigDecimal precoMax) {
        List<ProdutoDTO> produtos = produtoService.findByPrecoBetween(precoMin, precoMax);
        return ResponseEntity.ok(produtos);
    }
    
    @GetMapping("/estoque-baixo")
    @Operation(summary = "Buscar produtos com estoque baixo")
    public ResponseEntity<List<ProdutoDTO>> findByQuantidadeEstoqueLessThan(@RequestParam Integer quantidade) {
        List<ProdutoDTO> produtos = produtoService.findByQuantidadeEstoqueLessThan(quantidade);
        return ResponseEntity.ok(produtos);
    }
}