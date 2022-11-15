package com.cballestas.springboot.backend.apirest.service.impl;

import com.cballestas.springboot.backend.apirest.dto.ClienteDTO;
import com.cballestas.springboot.backend.apirest.dto.PaginatedResponseDTO;
import com.cballestas.springboot.backend.apirest.model.Cliente;
import com.cballestas.springboot.backend.apirest.repo.IClienteRepository;
import com.cballestas.springboot.backend.apirest.service.IClienteService;
import com.cballestas.springboot.backend.apirest.util.ConverterUtil;
import com.cballestas.springboot.backend.apirest.util.PaginatedResponseUtil;
import com.cballestas.springboot.backend.apirest.util.RestUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements IClienteService {

    static final Logger logger = LogManager.getLogger(ClienteServiceImpl.class);

    private final IClienteRepository clienteRepository;
    private final ConverterUtil converterUtil;

    public ClienteServiceImpl(IClienteRepository clienteRepository, ConverterUtil converterUtil) {
        this.clienteRepository = clienteRepository;
        this.converterUtil = converterUtil;
    }

    /**
     * Método que se encarga de obtener todos los clientes
     *
     * @return lista de clientes
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<PaginatedResponseDTO<ClienteDTO>> listarClientes(int page, int size, String sortOrder,
                                                                           String sortDir) {
        logger.info("Cargando listado de clientes...");

        // Ordenación de registros
        Sort sort = sortDir.equalsIgnoreCase(Direction.ASC.name()) ? Sort.by(sortOrder).ascending()
                : Sort.by(sortOrder).descending();

        // Se crea objeto pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Cliente> clientes = clienteRepository.findAll(pageable);

        List<ClienteDTO> listadoClientes = clientes.getContent().stream().map(
                        cliente -> converterUtil.mapearDTO(cliente, ClienteDTO.class)
                )
                .collect(Collectors.toList());

        // Se genera DTO con los datos de la paginación
        PaginatedResponseDTO<ClienteDTO> paginatedResponseDTO = PaginatedResponseUtil.buildResponse(clientes,
                listadoClientes);

        logger.info("Listado de clientes cargado...");
        return ResponseEntity.ok(paginatedResponseDTO);
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
        return ResponseEntity.ok(
                converterUtil.mapearDTO(
                        RestUtil.checkFound(clienteRepository.findById(id), id),
                        ClienteDTO.class
                )
        );
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

        // Se verifica si ya existe un cliente con el email registrado
        RestUtil.checkExistingEmail(clienteRepository.findByEmailAndId(clienteDTO.getEmail(), 0L));

        Cliente cliente = converterUtil.mapearEntidad(clienteDTO, Cliente.class);
        cliente.setFechaNacimiento(LocalDate.parse(clienteDTO.getFechaNacimiento()));

        Cliente nuevoCliente = clienteRepository.save(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                converterUtil.mapearDTO(nuevoCliente, ClienteDTO.class)
        );
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

        // Se verifica si ya existe un cliente con el email registrado
        RestUtil.checkExistingEmail(clienteRepository.findByEmailAndId(clienteDTO.getEmail(), id));

        cliente.setNombre(clienteDTO.getNombre());
        cliente.setApellido(clienteDTO.getApellido());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setFechaNacimiento(LocalDate.parse(clienteDTO.getFechaNacimiento()));

        Cliente clientActualizado = clienteRepository.save(cliente);

        logger.info("Cliente actualizado con éxito");

        return ResponseEntity.ok(
                converterUtil.mapearDTO(clientActualizado, ClienteDTO.class)
        );
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
