package com.er7system.er7bank.api.controller;

import com.er7system.er7bank.domain.model.Cliente;
import com.er7system.er7bank.domain.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {

    /**
     * #### Cliente
     * - **POST /clientes** - Criar um novo cliente
     * - **GET /clientes/{id}** - Obter detalhes de um cliente
     * - **PUT /clientes/{id}** - Atualizar informações de um cliente
     * - **DELETE /clientes/{id}** - Remover um cliente
     * - **GET /clientes** - Listar todos os clientes
     */

    private ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@RequestBody Cliente cliente) {
        Cliente clienteDB = clienteService.criar(cliente);
        // TODO: adicionar location na response
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @GetMapping("/{idCliente}")
    public Cliente buscar(@PathVariable Long idCliente) {
        return clienteService.buscar(idCliente);
    }

    @PutMapping("/{idCliente}")
    public Cliente atualizar(@PathVariable Long idCliente, @RequestBody Cliente clienteUpdate) {
        return clienteService.atualizar(idCliente, clienteUpdate);
    }

    @DeleteMapping("/{idCliente}")
    public void deletar(@PathVariable Long idCliente) {
        clienteService.deletar(idCliente);
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listar();
    }
}
