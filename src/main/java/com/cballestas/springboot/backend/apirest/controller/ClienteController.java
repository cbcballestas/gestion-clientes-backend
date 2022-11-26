package com.cballestas.springboot.backend.apirest.controller;

import com.cballestas.springboot.backend.apirest.dto.ClienteDTO;
import com.cballestas.springboot.backend.apirest.dto.PaginatedResponseDTO;
import com.cballestas.springboot.backend.apirest.dto.UpdatedProfilePhotoDTO;
import com.cballestas.springboot.backend.apirest.service.IClienteService;
import com.cballestas.springboot.backend.apirest.util.Constants;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("api/v1/clientes")
public class ClienteController {
    private final IClienteService clienteService;

    public ClienteController(IClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<ClienteDTO>> listarClientes(
            @RequestParam(name = "page", defaultValue = Constants.PAGE, required = false) int page,
            @RequestParam(name = "size", defaultValue = Constants.SIZE, required = false) int size,
            @RequestParam(name = "orderBy", defaultValue = Constants.SORT_ORDER, required = false) String sortOrder,
            @RequestParam(name = "sortDir", defaultValue = Constants.SORT_DIR, required = false) String sortDir) {
        return clienteService.listarClientes(page, size, sortOrder, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> buscarClientePorId(@PathVariable(name = "id") Long id) {
        return clienteService.buscarClientePorId(id);
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> guardarCliente(@Valid @RequestBody ClienteDTO clienteDTO) {
        return clienteService.guardarCliente(clienteDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable(name = "id") Long id,
                                                        @Valid @RequestBody ClienteDTO clienteDTO) {
        return clienteService.actualizarCliente(id, clienteDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable(name = "id") Long id) {
        return clienteService.eliminarCliente(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<UpdatedProfilePhotoDTO> subirFotoPerfil(
            @RequestParam(name = "archivo") MultipartFile archivo,
            @RequestParam(name = "id") Long id) {
        return clienteService.asignarFotoPerfil(archivo, id);
    }

    @GetMapping("/img")
    public ResponseEntity<Resource> obtenerFotoPerfil(@RequestParam(name = "foto") String nombreFoto) {
        return clienteService.obtenerFotoPerfil(nombreFoto);
    }
}
