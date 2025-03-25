package com.er7system.er7bank.domain.service;

import com.er7system.er7bank.domain.model.Cliente;
import com.er7system.er7bank.domain.repository.ClienteRepository;
import com.er7system.er7bank.domain.exception.ClienteNaoEncontradoException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente criar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente buscar(Long idCliente) {
        return clienteRepository.findById(idCliente).orElseThrow(() -> new ClienteNaoEncontradoException(idCliente));
    }

    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    public Cliente atualizar(Long idCliente, Cliente cliente) {
        Cliente clienteDB = buscar(idCliente);
        BeanUtils.copyProperties(cliente, clienteDB, "id");
        return clienteRepository.save(clienteDB);
    }

    // TODO: Alterar remocao fisica para remocao logica
    public void deletar(Long idCliente) {
        var cliente = clienteRepository.findById(idCliente);
        clienteRepository.deleteById(cliente.get().getId());
    }
}
