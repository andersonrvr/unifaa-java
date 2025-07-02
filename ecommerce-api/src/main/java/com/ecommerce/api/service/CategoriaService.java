package com.ecommerce.api.service;

import com.ecommerce.api.dto.CategoriaDTO;
import com.ecommerce.api.entity.Categoria;
import com.ecommerce.api.exception.ResourceNotFoundException;
import com.ecommerce.api.repository.CategoriaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    public List<CategoriaDTO> findAll() {
        return categoriaRepository.findAllOrderByDataCriacaoDesc()
                .stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
                .collect(Collectors.toList());
    }
    
    public CategoriaDTO findById(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
        return modelMapper.map(categoria, CategoriaDTO.class);
    }
    
    public CategoriaDTO save(CategoriaDTO categoriaDTO) {
        Categoria categoria = modelMapper.map(categoriaDTO, Categoria.class);
        categoria = categoriaRepository.save(categoria);
        return modelMapper.map(categoria, CategoriaDTO.class);
    }
    
    public CategoriaDTO update(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
        
        categoria.setNome(categoriaDTO.getNome());
        categoria.setDescricao(categoriaDTO.getDescricao());
        
        categoria = categoriaRepository.save(categoria);
        return modelMapper.map(categoria, CategoriaDTO.class);
    }
    
    public void delete(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
        categoriaRepository.delete(categoria);
    }
    
    public List<CategoriaDTO> findByNomeContaining(String nome) {
        return categoriaRepository.findByNomeContaining(nome)
                .stream()
                .map(categoria -> modelMapper.map(categoria, CategoriaDTO.class))
                .collect(Collectors.toList());
    }
    
    // Método para uso interno
    public Categoria findEntityById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
    }
}