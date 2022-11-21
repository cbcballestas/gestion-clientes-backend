package com.cballestas.springboot.backend.apirest.service.impl;

import com.cballestas.springboot.backend.apirest.dto.ClienteDTO;
import com.cballestas.springboot.backend.apirest.dto.PaginatedResponseDTO;
import com.cballestas.springboot.backend.apirest.dto.UpdatedProfilePhotoDTO;
import com.cballestas.springboot.backend.apirest.exception.*;
import com.cballestas.springboot.backend.apirest.model.Cliente;
import com.cballestas.springboot.backend.apirest.repo.IClienteRepository;
import com.cballestas.springboot.backend.apirest.service.IClienteService;
import com.cballestas.springboot.backend.apirest.util.Constants;
import com.cballestas.springboot.backend.apirest.util.ConverterUtil;
import com.cballestas.springboot.backend.apirest.util.PaginatedResponseUtil;
import com.cballestas.springboot.backend.apirest.util.RestUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.cballestas.springboot.backend.apirest.util.Constants.*;

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
     * Método que se encarga de cargar la foto de perfil de un cliente
     *
     * @param id      Id cliente
     * @param archivo Objeto que contiene el archivo a cargar
     * @return Objeto UpdatedProfilePhotoDTO
     */
    @Override
    @Transactional
    public ResponseEntity<UpdatedProfilePhotoDTO> asignarFotoPerfil(MultipartFile archivo, Long id) {
        Cliente cliente = RestUtil.checkFound(clienteRepository.findById(id), id);
        logger.info("Cliente encontrado [{}]", cliente);

        if (archivo.isEmpty()) {
            logger.error(Constants.ERROR_MESSAGE_UPLOAD_FILE);
            throw new GlobalException("DEBE seleccionar un archivo", HttpStatus.BAD_REQUEST);
        }

        logger.info(Constants.MESSAGE_INIT_FILE_UPLOAD);

        // Se genera nombre único del archivo
        String nombreArchivo = UUID.randomUUID() + "_" + Objects.requireNonNull(
                archivo.getOriginalFilename()).replace(" ", "");
        Path path = Paths.get(UPLOAD_DIR).resolve(nombreArchivo).toAbsolutePath();

        try {
            Files.copy(archivo.getInputStream(), path);
            logger.info(Constants.MESSAGE_FINALIZE_FILE_UPLOAD);
        } catch (IOException e) {
            logger.error(Constants.ERROR_MESSAGE_UPLOAD_FILE_DETAILED, e.getCause().getMessage(), e);
            throw new UploadFileException(Constants.ERROR_MESSAGE_UPLOAD_FILE, e);
        }

        // Se verifica si el cliente tiene una foto ya registrada
        deleteUserProfilePhoto(cliente);

        // Se agrega nombre del archivo en base de datos
        cliente.setFoto(nombreArchivo);
        clienteRepository.save(cliente);

        UpdatedProfilePhotoDTO updatedProfilePhotoDTO = new UpdatedProfilePhotoDTO(
                converterUtil.mapearDTO(cliente, ClienteDTO.class),
                Constants.MESSAGE_UPDATED_PROFILE_PHOTO
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedProfilePhotoDTO);
    }

    /**
     * Método que se encarga de retornar la foto de perfil de un cliente
     *
     * @param nombreFoto nombre de la foto
     * @return recurso con URL de la foto de perfil
     */
    @Override
    public ResponseEntity<Resource> obtenerFotoPerfil(String nombreFoto) {

        // Se obtiene ruta del archivo
        Path rutaArchivo = Paths.get(UPLOAD_DIR).resolve(nombreFoto).toAbsolutePath();

        // Se verifica si el archivo existe
        File archivo = rutaArchivo.toFile();

        if (!(archivo.exists() && archivo.canRead())) {
            logger.error(ERROR_MESSAGE_RESOURCE_NOT_FOUND);
            throw new ResourceNotFoundException(ERROR_MESSAGE_RESOURCE_NOT_FOUND);
        }

        //Se genera url del recurso
        Resource resource;

        try {
            resource = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            throw new ResourceErrorException(e.getCause().getMessage());
        }

        // Se verifica si el recurso existe y también si es accesible
        if (!resource.exists() && !resource.isReadable()) {
            logger.error(ERROR_CARGA_IMAGEN_DETAILED, nombreFoto);
            throw new ResourceErrorException(ERROR_CARGA_IMAGEN + nombreFoto);
        }

        // Se crean las cabeceras http
        HttpHeaders cabeceras = new HttpHeaders();
        cabeceras.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + resource.getFilename() + "\"");

        return new ResponseEntity<>(resource, cabeceras, HttpStatus.OK);
    }

    /**
     * Método que se encarga de eliminar un registro por ID
     *
     * @param id id registro
     */
    @Override
    @Transactional
    public ResponseEntity<Void> eliminarCliente(Long id) {
        Cliente cliente = RestUtil.checkFound(clienteRepository.findById(id), id);

        // Se verifica si el cliente tiene una foto ya registrada
        deleteUserProfilePhoto(cliente);

        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Método que se encarga de eliminar la foto de perfil de un cliente
     *
     * @param cliente Objeto Cliente
     */
    private void deleteUserProfilePhoto(Cliente cliente) {
        String nombreFotoAnterior = cliente.getFoto();

        logger.info(Constants.MESSAGE_INICIO_BORRADO_FOTO_PERFIL);

        if (nombreFotoAnterior != null && nombreFotoAnterior.length() > 0) {
            Path rutaFotoAnterior = Paths.get(UPLOAD_DIR).resolve(nombreFotoAnterior).toAbsolutePath();
            File archivoAnterior = rutaFotoAnterior.toFile();
            if (archivoAnterior.exists() && archivoAnterior.canRead()) {
                try {
                    Files.delete(rutaFotoAnterior);
                } catch (IOException e) {
                    logger.error(Constants.ERROR_MESSAGE_DELETE_FILE_DETAILED, e.getCause().getMessage(), e);
                    throw new DeleteFileException(Constants.ERROR_MESSAGE_DELETE_FILE, e);
                }
                logger.info(Constants.MESSAGE_FINALIZA_BORRADO_FOTO_PERFIL);
            }
        }
    }
}
