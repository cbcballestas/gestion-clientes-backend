package com.cballestas.springboot.backend.apirest.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cballestas.springboot.backend.apirest.dto.ClienteDTO;
import com.cballestas.springboot.backend.apirest.model.Cliente;

@Component
public class ClienteMapperUtil {

	@Autowired
	private ModelMapper mapper;

	/**
	 * Método que se encarga de mapear un objeto a formato DTO
	 *
	 * @param object objeto a mapear
	 * @return objeto mapeado
	 */
	public ClienteDTO mapearDTO(Cliente object) {
		return mapper.map(object, ClienteDTO.class);
	}

	/**
	 * Método que se encarga de mapear un objeto a formato entidad
	 *
	 * @param object objeto a mapear
	 * @return objeto mapeado
	 */
	public Cliente mapearEntidad(ClienteDTO object) {
		return mapper.map(object, Cliente.class);
	}

}
