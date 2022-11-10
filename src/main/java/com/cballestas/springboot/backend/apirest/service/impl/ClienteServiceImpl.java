package com.cballestas.springboot.backend.apirest.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cballestas.springboot.backend.apirest.dto.ClienteDTO;
import com.cballestas.springboot.backend.apirest.model.Cliente;
import com.cballestas.springboot.backend.apirest.repo.IClienteRepository;
import com.cballestas.springboot.backend.apirest.service.IClienteService;
import com.cballestas.springboot.backend.apirest.util.ClienteMapperUtil;
import com.cballestas.springboot.backend.apirest.util.RestUtil;

@Service
public class ClienteServiceImpl implements IClienteService {

	static final Logger logger = LogManager.getLogger(ClienteServiceImpl.class);

	private final IClienteRepository clienteRepository;
	private final ClienteMapperUtil clienteMapperUtil;

	@Autowired
	public ClienteServiceImpl(IClienteRepository clienteRepository, ClienteMapperUtil clienteMapperUtil) {
		this.clienteRepository = clienteRepository;
		this.clienteMapperUtil = clienteMapperUtil;
	}

	/**
	 * Método que se encarga de obtener todos los clientes
	 *
	 * @return lista de clientes
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<List<ClienteDTO>> listarClientes() {
		logger.info("Cargando listado de clientes...");
		List<ClienteDTO> clientes = clienteRepository.findAll().stream().map(clienteMapperUtil::mapearDTO)
				.collect(Collectors.toList());
		logger.info("Listado de clientes cargado...");
		return ResponseEntity.ok(clientes);
	}

	/**
	 * Método que se encarga de consultar un cliente por ID
	 *
	 * @param id id Registro
	 * @return objeto Cliente DTO
	 */
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ClienteDTO> buscarClientePorId(Long id) {
		logger.info("Buscando cliente por ID...");
		return ResponseEntity.ok(clienteMapperUtil.mapearDTO(RestUtil.checkFound(clienteRepository.findById(id), id)));
	}

	/**
	 * Método que se encarga de guardar un cliente en base de datos
	 *
	 * @param clienteDTO objeto ClienteDTO con los datos a guardar
	 * @return objeto ClienteDTO con datos del cliente guardado
	 */
	@Override
	@Transactional
	public ResponseEntity<ClienteDTO> guardarCliente(ClienteDTO clienteDTO) {

		Cliente cliente = clienteMapperUtil.mapearEntidad(clienteDTO);

		Cliente nuevoCliente = clienteRepository.save(cliente);

		return ResponseEntity.status(HttpStatus.CREATED).body(clienteMapperUtil.mapearDTO(nuevoCliente));
	}

	/**
	 * Método que se encarga de actualizar los datos de un cliente
	 *
	 * @param id         id registro
	 * @param clienteDTO objeto ClienteDTO con los datos a actualizar
	 * @return objeto ClienteDTO con datos del cliente actualizado
	 */
	@Override
	@Transactional
	public ResponseEntity<ClienteDTO> actualizarCliente(Long id, ClienteDTO clienteDTO) {
		Cliente cliente = RestUtil.checkFound(clienteRepository.findById(id), id);
		logger.info("Cliente encontrado [{}]", cliente);

		cliente.setNombre(clienteDTO.getNombre());
		cliente.setApellido(clienteDTO.getApellido());
		cliente.setEmail(clienteDTO.getEmail());

		Cliente clientActualizado = clienteRepository.save(cliente);

		logger.info("Cliente actualizado con éxito");

		return ResponseEntity.ok(clienteMapperUtil.mapearDTO(clientActualizado));
	}

	/**
	 * Método que se encarga de eliminar un registro por ID
	 *
	 * @param id id registro
	 */
	@Override
	@Transactional
	public ResponseEntity<Void> eliminarCliente(Long id) {
		RestUtil.checkFound(clienteRepository.findById(id), id);
		clienteRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
