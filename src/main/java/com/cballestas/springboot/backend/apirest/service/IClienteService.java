package com.cballestas.springboot.backend.apirest.service;

import com.cballestas.springboot.backend.apirest.dto.ClienteDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IClienteService {
    ResponseEntity<List<ClienteDTO>> listarClientes();

    ResponseEntity<ClienteDTO> buscarClientePorId(Long id);

    ResponseEntity<ClienteDTO> guardarCliente(ClienteDTO clienteDTO);

    ResponseEntity<ClienteDTO> actualizarCliente(Long id, ClienteDTO clienteDTO);

    ResponseEntity<Void> eliminarCliente(Long id);
}
