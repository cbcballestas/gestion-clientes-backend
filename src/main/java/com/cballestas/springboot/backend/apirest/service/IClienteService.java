package com.cballestas.springboot.backend.apirest.service;

import com.cballestas.springboot.backend.apirest.dto.ClienteDTO;
import com.cballestas.springboot.backend.apirest.dto.PaginatedResponseDTO;
import com.cballestas.springboot.backend.apirest.dto.UpdatedProfilePhotoDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IClienteService {
    ResponseEntity<PaginatedResponseDTO<ClienteDTO>> listarClientes(int page, int size, String sortOrder, String sortDir);

    ResponseEntity<ClienteDTO> buscarClientePorId(Long id);

    ResponseEntity<ClienteDTO> guardarCliente(ClienteDTO clienteDTO);

    ResponseEntity<ClienteDTO> actualizarCliente(Long id, ClienteDTO clienteDTO);

    ResponseEntity<UpdatedProfilePhotoDTO> asignarFotoPerfil(MultipartFile archivo, Long id);

    ResponseEntity<Resource> obtenerFotoPerfil(String nombreFoto);

    ResponseEntity<Void> eliminarCliente(Long id);
}
