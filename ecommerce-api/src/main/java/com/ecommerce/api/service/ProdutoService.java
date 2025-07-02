package com.ecommerce.api.service;

import com.ecommerce.api.dto.ProdutoDTO;
import com.ecommerce.api.entity.Categoria;
import com.ecommerce.api.entity.Produto;
import com.ecommerce.api.exception.ResourceNotFoundException;
import com.ecommerce.api.repository.ProdutoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProdutoService {
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private CategoriaService categoriaService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    public List<ProdutoDTO> findAll() {
        return produtoRepository.findByAtivoTrue()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ProdutoDTO findById(Long id) {
        Produto produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        return convertToDTO(produto);
    }
    
    public List<ProdutoDTO> findByCategoriaId(Long categoriaId) {
        // Verificar se a categoria existe
        categoriaService.findById(categoriaId);
        
        return produtoRepository.findByCategoriaIdAndAtivoTrueOrderByNome(categoriaId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        Categoria categoria = categoriaService.findEntityById(produtoDTO.getCategoriaId());
        
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
        produto.setAtivo(produtoDTO.getAtivo());
        produto.setCategoria(categoria);
        
        produto = produtoRepository.save(produto);
        return convertToDTO(produto);
    }
    
    public ProdutoDTO update(Long id, ProdutoDTO produtoDTO) {
        Produto produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        
        Categoria categoria = categoriaService.findEntityById(produtoDTO.getCategoriaId());
        
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setPreco(produtoDTO.getPreco());
        produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
        produto.setAtivo(produtoDTO.getAtivo());
        produto.setCategoria(categoria);
        
        produto = produtoRepository.save(produto);
        return convertToDTO(produto);
    }
    
    public void delete(Long id) {
        Produto produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        produto.setAtivo(false); // Soft delete
        produtoRepository.save(produto);
    }
    
    public List<ProdutoDTO> findByNomeContaining(String nome) {
        return produtoRepository.findByNomeContainingAndAtivoTrue(nome)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<ProdutoDTO> findByPrecoBetween(BigDecimal precoMin, BigDecimal precoMax) {
        return produtoRepository.findByPrecoBetweenAndAtivoTrue(precoMin, precoMax)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<ProdutoDTO> findByQuantidadeEstoqueLessThan(Integer quantidade) {
        return produtoRepository.findByQuantidadeEstoqueLessThanAndAtivoTrue(quantidade)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    private ProdutoDTO convertToDTO(Produto produto) {
        ProdutoDTO dto = modelMapper.map(produto, ProdutoDTO.class);
        dto.setCategoriaId(produto.getCategoria().getId());
        return dto;
    }
}