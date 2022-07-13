package com.practicas.springboot.backend.apirest.service;

import org.springframework.http.ResponseEntity;

import com.practicas.springboot.backend.apirest.dto.ClienteDTO;
import com.practicas.springboot.backend.apirest.dto.PaginacionRespuestaDTO;
import com.practicas.springboot.backend.apirest.util.JsonResponse;

public interface IClienteService {

	ResponseEntity<JsonResponse<PaginacionRespuestaDTO<ClienteDTO>>> listarClientes(int numeroPagina, int medidaPagina,
			String sortOrder, String sortDir);

	ResponseEntity<JsonResponse<ClienteDTO>> listarClientePorId(Long id);

	ResponseEntity<JsonResponse<ClienteDTO>> registrarCliente(ClienteDTO clienteDTO);

	ResponseEntity<JsonResponse<ClienteDTO>> actualizarCliente(ClienteDTO clienteDTO, Long id);

	ResponseEntity<Void> eliminarCliente(Long id);
}
