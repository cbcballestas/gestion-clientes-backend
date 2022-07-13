package com.practicas.springboot.backend.apirest.util;

import org.springframework.stereotype.Component;

import com.practicas.springboot.backend.apirest.dto.ClienteDTO;
import com.practicas.springboot.backend.apirest.model.Cliente;

@Component
public class UtilConverter {

	/**
	 * Método que se encarga de mapear a objeto ClienteDTO
	 * 
	 * @param cliente
	 * @return objeto ClienteDTO
	 */
	public ClienteDTO mapearClienteDTO(Cliente cliente) {
		ClienteDTO clienteDTO = new ClienteDTO();

		clienteDTO.setId(cliente.getId());
		clienteDTO.setNombre(cliente.getNombre());
		clienteDTO.setApellido(cliente.getApellido());
		clienteDTO.setEmail(cliente.getEmail());
		clienteDTO.setCreateAt(cliente.getCreateAt());

		return clienteDTO;
	}

	/**
	 * Método que se encarga de mapear a objeto Cliente
	 * 
	 * @param clienteDTO
	 * @return objeto Cliente
	 */
	public Cliente mapearCliente(ClienteDTO clienteDTO) {
		Cliente cliente = new Cliente();

		cliente.setNombre(clienteDTO.getNombre());
		cliente.setApellido(clienteDTO.getApellido());
		cliente.setEmail(clienteDTO.getEmail());
		cliente.setCreateAt(clienteDTO.getCreateAt());

		return cliente;
	}

}
