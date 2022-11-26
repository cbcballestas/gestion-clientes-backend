package com.cballestas.springboot.backend.apirest.service.impl;

import com.cballestas.springboot.backend.apirest.exception.DeleteFileException;
import com.cballestas.springboot.backend.apirest.service.IUploadFileService;
import com.cballestas.springboot.backend.apirest.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import static com.cballestas.springboot.backend.apirest.util.Constants.*;

@Service
@Slf4j
public class UploadServiceImpl implements IUploadFileService {

    @Override
    public Resource cargarArchivo(String nombreFoto) throws MalformedURLException {
        // Se obtiene ruta del archivo
        Path rutaArchivo = obtenerPath(nombreFoto);

        //Se genera url del recurso
        Resource resource = new UrlResource(rutaArchivo.toUri());

        // Se verifica si el recurso existe y también si es accesible
        if (!resource.exists() && !resource.isReadable()) {
            rutaArchivo = Paths.get(STATIC_FILES_DIR).resolve(NO_PROFILE_PHOTO_FILE).toAbsolutePath();

            resource = new UrlResource(rutaArchivo.toUri());
            log.error(ERROR_CARGA_IMAGEN_DETAILED, nombreFoto);
        }
        return resource;
    }

    @Override
    public String guardarArchivo(MultipartFile archivo) throws IOException {

        // Se genera nombre único del archivo
        String nombreArchivo = UUID.randomUUID() + "_" + Objects.requireNonNull(archivo.getOriginalFilename())
                .replace(" ", "");
        Path path = obtenerPath(nombreArchivo);

        Files.copy(archivo.getInputStream(), path);

        return nombreArchivo;
    }

    @Override
    public void eliminarArchivo(String nombreFoto) throws DeleteFileException {
        if (nombreFoto != null && nombreFoto.length() > 0) {
            Path rutaFotoAnterior = obtenerPath(nombreFoto);
            File archivoAnterior = rutaFotoAnterior.toFile();
            if (archivoAnterior.exists() && archivoAnterior.canRead()) {
                log.info(MESSAGE_INICIO_BORRADO_FOTO_PERFIL);
                try {
                    Files.delete(rutaFotoAnterior);
                    log.info(MESSAGE_FINALIZA_BORRADO_FOTO_PERFIL);
                } catch (IOException e) {
                    log.error(Constants.ERROR_MESSAGE_DELETE_FILE_DETAILED, e.getCause().getMessage(), e);
                    throw new DeleteFileException(Constants.ERROR_MESSAGE_DELETE_FILE, e);
                }
            }
        }
    }

    @Override
    public Path obtenerPath(String nombreFoto) {
        return Paths.get(UPLOAD_DIR).resolve(nombreFoto).toAbsolutePath();
    }
}
