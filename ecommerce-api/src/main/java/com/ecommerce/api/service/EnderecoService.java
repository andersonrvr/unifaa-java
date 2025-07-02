package com.ecommerce.api.service;

import com.ecommerce.api.dto.EnderecoDTO;
import com.ecommerce.api.entity.Cliente;
import com.ecommerce.api.entity.Endereco;
import com.ecommerce.api.exception.ResourceNotFoundException;
import com.ecommerce.api.repository.EnderecoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EnderecoService {
    
    @Autowired
    private EnderecoRepository enderecoRepository;
    
    @Autowired
    private ClienteService clienteService;
    
    @Autowired
    private ModelMapper modelMapper;
    
    public List<EnderecoDTO> findAll() {
        return enderecoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public EnderecoDTO findById(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID: " + id));
        return convertToDTO(endereco);
    }
    
    public List<EnderecoDTO> findByClienteId(Long clienteId) {
        // Verificar se o cliente existe
        clienteService.findById(clienteId);
        
        return enderecoRepository.findByClienteIdOrderByDataCriacaoDesc(clienteId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public EnderecoDTO save(EnderecoDTO enderecoDTO) {
        Cliente cliente = clienteService.findEntityById(enderecoDTO.getClienteId());
        
        Endereco endereco = new Endereco();
        endereco.setCep(enderecoDTO.getCep());
        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setComplemento(enderecoDTO.getComplemento());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setCliente(cliente);
        
        endereco = enderecoRepository.save(endereco);
        return convertToDTO(endereco);
    }
    
    public EnderecoDTO update(Long id, EnderecoDTO enderecoDTO) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID: " + id));
        
        Cliente cliente = clienteService.findEntityById(enderecoDTO.getClienteId());
        
        endereco.setCep(enderecoDTO.getCep());
        endereco.setRua(enderecoDTO.getRua());
        endereco.setNumero(enderecoDTO.getNumero());
        endereco.setBairro(enderecoDTO.getBairro());
        endereco.setComplemento(enderecoDTO.getComplemento());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setCliente(cliente);
        
        endereco = enderecoRepository.save(endereco);
        return convertToDTO(endereco);
    }
    
    public void delete(Long id) {
        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID: " + id));
        enderecoRepository.delete(endereco);
    }
    
    public List<EnderecoDTO> findByCep(String cep) {
        return enderecoRepository.findByCep(cep)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<EnderecoDTO> findByCidadeAndEstado(String cidade, String estado) {
        return enderecoRepository.findByCidadeAndEstado(cidade, estado)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public Long countByClienteId(Long clienteId) {
        return enderecoRepository.countByClienteId(clienteId);
    }
    
    private EnderecoDTO convertToDTO(Endereco endereco) {
        EnderecoDTO dto = modelMapper.map(endereco, EnderecoDTO.class);
        dto.setClienteId(endereco.getCliente().getId());
        return dto;
    }
}