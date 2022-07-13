package com.practicas.springboot.backend.apirest.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.practicas.springboot.backend.apirest.dto.ClienteDTO;
import com.practicas.springboot.backend.apirest.dto.PaginacionRespuestaDTO;
import com.practicas.springboot.backend.apirest.exception.GlobalException;
import com.practicas.springboot.backend.apirest.exception.ResourceNotFoundException;
import com.practicas.springboot.backend.apirest.model.Cliente;
import com.practicas.springboot.backend.apirest.repo.IClienteRepo;
import com.practicas.springboot.backend.apirest.service.IClienteService;
import com.practicas.springboot.backend.apirest.util.ConstantsUtil;
import com.practicas.springboot.backend.apirest.util.JsonResponse;
import com.practicas.springboot.backend.apirest.util.UtilConverter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements IClienteService {

	private final IClienteRepo repo;
	private final UtilConverter utilConverter;
	private final ConstantsUtil utilKey;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<JsonResponse<PaginacionRespuestaDTO<ClienteDTO>>> listarClientes(int numeroPagina,
			int medidaPagina, String sortOrder, String sortDir) {

		// Paginación registros

		Sort sort = sortDir.equalsIgnoreCase(Direction.ASC.name()) ? Sort.by(sortOrder).ascending()
				: Sort.by(sortOrder).descending();

		Pageable pageable = PageRequest.of(numeroPagina, medidaPagina, sort);

		Page<Cliente> clientes = repo.findAll(pageable);

		List<ClienteDTO> listaClientes = clientes.getContent().stream()
				.map(cliente -> utilConverter.mapearClienteDTO(cliente)).collect(Collectors.toList());

		// Se arma objeto de paginación para enviar como respuesta al frontend

		PaginacionRespuestaDTO<ClienteDTO> paginacionRespuestaDTO = new PaginacionRespuestaDTO<>();
		paginacionRespuestaDTO.setNumeroPagina(clientes.getNumber());
		paginacionRespuestaDTO.setMedidaPagina(clientes.getSize());
		paginacionRespuestaDTO.setTotalElementos(clientes.getTotalElements());
		paginacionRespuestaDTO.setTotalPaginas(clientes.getTotalPages());
		paginacionRespuestaDTO.setEsUltima(clientes.isLast());
		paginacionRespuestaDTO.setLista(listaClientes);

		return new ResponseEntity<>(new JsonResponse<PaginacionRespuestaDTO<ClienteDTO>>(utilKey.STATUS_SUCCESS,
				utilKey.MESSAGE_SUCCESS, paginacionRespuestaDTO), HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<JsonResponse<ClienteDTO>> listarClientePorId(Long id) {

		ClienteDTO clienteDTO = utilConverter.mapearClienteDTO(repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CLIENTE CON ID " + id + ", NO HA SIDO ENCONTRADO")));

		return new ResponseEntity<>(
				new JsonResponse<ClienteDTO>(utilKey.STATUS_SUCCESS, utilKey.MESSAGE_SUCCESS, clienteDTO),
				HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<JsonResponse<ClienteDTO>> registrarCliente(ClienteDTO clienteDTO) {

		// Se verifica si ya existe un cliente con el email ingresado
		Cliente clienteBuscar = repo.findByEmail(clienteDTO.getEmail().trim(), Long.valueOf(0));

		if (clienteBuscar != null) {
			throw new GlobalException("Ya existe un cliente con el email especificado", HttpStatus.BAD_REQUEST);
		}

		Cliente cliente = utilConverter.mapearCliente(clienteDTO);

		ClienteDTO nuevoCliente = utilConverter.mapearClienteDTO(repo.save(cliente));

		return new ResponseEntity<>(
				new JsonResponse<ClienteDTO>(utilKey.STATUS_SUCCESS, utilKey.MESSAGE_SUCCESS, nuevoCliente),
				HttpStatus.CREATED);
	}

	@Override
	@Transactional
	public ResponseEntity<JsonResponse<ClienteDTO>> actualizarCliente(ClienteDTO clienteDTO, Long id) {

		// Se verifica si ya existe un cliente con el email ingresado ( diferente al
		// email del registro actual)
		Cliente clienteBuscar = repo.findByEmail(clienteDTO.getEmail().trim(), clienteDTO.getId());

		if (clienteBuscar != null) {
			throw new GlobalException("Ya existe un cliente con el email especificado", HttpStatus.BAD_REQUEST);
		}

		Cliente cliente = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CLIENTE CON ID " + id + ", NO HA SIDO ENCONTRADO"));

		// Se actualizan los datos del cliente
		cliente.setNombre(clienteDTO.getNombre());
		cliente.setApellido(clienteDTO.getApellido());
		cliente.setEmail(clienteDTO.getEmail());
//		cliente.setCreateAt(clienteDTO.getCreateAt());

		ClienteDTO clienteAct = utilConverter.mapearClienteDTO(repo.save(cliente));

		return new ResponseEntity<>(
				new JsonResponse<ClienteDTO>(utilKey.STATUS_SUCCESS, utilKey.MESSAGE_SUCCESS, clienteAct),
				HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<Void> eliminarCliente(Long id) {

		Cliente cliente = repo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CLIENTE CON ID " + id + ", NO HA SIDO ENCONTRADO"));

		repo.delete(cliente);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
