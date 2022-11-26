package com.cballestas.springboot.backend.apirest.service;

import com.cballestas.springboot.backend.apirest.exception.DeleteFileException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

public interface IUploadFileService {
    Resource cargarArchivo(String nombreFoto) throws MalformedURLException;

    String guardarArchivo(MultipartFile archivo) throws IOException;

    void eliminarArchivo(String nombreFoto) throws DeleteFileException;

    Path obtenerPath(String nombreFoto);

}
