package com.cballestas.springboot.backend.apirest.service;

import org.springframework.http.ResponseEntity;

import com.cballestas.springboot.backend.apirest.dto.ClienteDTO;
import com.cballestas.springboot.backend.apirest.dto.PaginatedResponseDTO;

public interface IClienteService {
	ResponseEntity<PaginatedResponseDTO<ClienteDTO>> listarClientes(int page, int size, String sortOrder, String sortDir);

	ResponseEntity<ClienteDTO> buscarClientePorId(Long id);

	ResponseEntity<ClienteDTO> guardarCliente(ClienteDTO clienteDTO);

	ResponseEntity<ClienteDTO> actualizarCliente(Long id, ClienteDTO clienteDTO);

	ResponseEntity<Void> eliminarCliente(Long id);
}
