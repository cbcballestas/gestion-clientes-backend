package com.practicas.springboot.backend.apirest.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.practicas.springboot.backend.apirest.dto.ClienteDTO;
import com.practicas.springboot.backend.apirest.dto.PaginacionRespuestaDTO;
import com.practicas.springboot.backend.apirest.service.IClienteService;
import com.practicas.springboot.backend.apirest.util.ConstantsUtil;
import com.practicas.springboot.backend.apirest.util.JsonResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = { "http://localhost:4200" })
@RequiredArgsConstructor
public class ClienteController {

	private final IClienteService service;

	/**
	 * Método que se encarga de devolver una lista con los clientes registrados
	 * 
	 * @return lista ClienteDTO
	 */
	@GetMapping
	public ResponseEntity<JsonResponse<PaginacionRespuestaDTO<ClienteDTO>>> listarClientes(
			@RequestParam(name = "page", defaultValue = ConstantsUtil.NUMERO_PAGINA, required = false) int numeroPagina,
			@RequestParam(name = "size", defaultValue = ConstantsUtil.MEDIDA_PAGINA, required = false) int medidaPagina,
			@RequestParam(name = "orderBy", defaultValue = ConstantsUtil.ORDER_BY, required = false) String sortOrder,
			@RequestParam(name = "sortDir", defaultValue = ConstantsUtil.SORT_DIR, required = false) String sortDir) {
		return service.listarClientes(numeroPagina, medidaPagina, sortOrder, sortDir);
	}

	/**
	 * Método que devuelve un registro consultado con un ID específico
	 * 
	 * @param id
	 * @return Objeto ClienteDTO
	 */
	@GetMapping("/{id}")
	public ResponseEntity<JsonResponse<ClienteDTO>> listarClientePorId(@PathVariable("id") Long id) {
		return service.listarClientePorId(id);
	}

	/**
	 * Método que se encarga de realizar el registro de un cliente
	 * 
	 * @param clienteDTO
	 * @return objeto clienteDTO
	 */
	@PostMapping
	public ResponseEntity<JsonResponse<ClienteDTO>> registrarCliente(@RequestBody @Valid ClienteDTO clienteDTO) {
		return service.registrarCliente(clienteDTO);
	}

	/**
	 * Método que se encarga de actualizar los datos de un cliente
	 * 
	 * @param clienteDTO
	 * @param id
	 * @return objeto clienteDTO
	 */
	@PutMapping("/{id}")
	public ResponseEntity<JsonResponse<ClienteDTO>> actualizarCliente(@RequestBody @Valid ClienteDTO clienteDTO,
			@PathVariable("id") Long id) {
		return service.actualizarCliente(clienteDTO, id);
	}

	/**
	 * Método que se encarga de eliminar un cliente en la base de datos
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarCliente(@PathVariable("id") Long id) {
		return service.eliminarCliente(id);
	}

}
