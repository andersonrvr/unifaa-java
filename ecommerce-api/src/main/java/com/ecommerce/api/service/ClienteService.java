package com.ecommerce.api.service;

import com.ecommerce.api.dto.ClienteDTO;
import com.ecommerce.api.entity.Cliente;
import com.ecommerce.api.exception.CpfAlreadyExistsException;
import com.ecommerce.api.exception.EmailAlreadyExistsException;
import com.ecommerce.api.exception.ResourceNotFoundException;
import com.ecommerce.api.repository.ClienteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private ModelMapper modelMapper;
    
    public List<ClienteDTO> findAll() {
        return clienteRepository.findByAtivoTrue()
                .stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }
    
    public ClienteDTO findById(Long id) {
        Cliente cliente = clienteRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        return modelMapper.map(cliente, ClienteDTO.class);
    }
    
    public ClienteDTO save(ClienteDTO clienteDTO) {
        // Verificar se email já existe
        if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Email já está em uso: " + clienteDTO.getEmail());
        }
        
        // Verificar se CPF já existe
        if (clienteRepository.existsByCpf(clienteDTO.getCpf())) {
            throw new CpfAlreadyExistsException("CPF já está em uso: " + clienteDTO.getCpf());
        }
        
        Cliente cliente = modelMapper.map(clienteDTO, Cliente.class);
        cliente = clienteRepository.save(cliente);
        return modelMapper.map(cliente, ClienteDTO.class);
    }
    
    public ClienteDTO update(Long id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        
        // Verificar se email já existe para outro cliente
        if (clienteRepository.existsByEmailAndIdNot(clienteDTO.getEmail(), id)) {
            throw new EmailAlreadyExistsException("Email já está em uso: " + clienteDTO.getEmail());
        }
        
        // Verificar se CPF já existe para outro cliente
        if (clienteRepository.existsByCpfAndIdNot(clienteDTO.getCpf(), id)) {
            throw new CpfAlreadyExistsException("CPF já está em uso: " + clienteDTO.getCpf());
        }
        
        cliente.setNome(clienteDTO.getNome());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setAtivo(clienteDTO.getAtivo());
        
        cliente = clienteRepository.save(cliente);
        return modelMapper.map(cliente, ClienteDTO.class);
    }
    
    public void delete(Long id) {
        Cliente cliente = clienteRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        cliente.setAtivo(false); // Soft delete
        clienteRepository.save(cliente);
    }
    
    public List<ClienteDTO> findByNomeContaining(String nome) {
        return clienteRepository.findByNomeContainingAndAtivoTrue(nome)
                .stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }
    
    public ClienteDTO findByEmail(String email) {
        Cliente cliente = clienteRepository.findByEmailAndAtivoTrue(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com email: " + email));
        return modelMapper.map(cliente, ClienteDTO.class);
    }
    
    // Método para uso interno
    public Cliente findEntityById(Long id) {
        return clienteRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
    }
}