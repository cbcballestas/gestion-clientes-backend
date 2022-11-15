package com.cballestas.springboot.backend.apirest.util;

import com.cballestas.springboot.backend.apirest.exception.GlobalException;
import com.cballestas.springboot.backend.apirest.exception.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class RestUtil {

    static final Logger logger = LogManager.getLogger(RestUtil.class);

    private RestUtil() {
        throw new IllegalStateException("Utility Class");
    }

    /**
     * Método que se encarga de verificar si el recurso especificado existe
     *
     * @param resource recurso a verificar
     * @param id       ID registro
     * @param <T>      tipo del recurso a verificar
     * @return objeto recurso encontrado, de lo contrario retorna una excepción
     */
    public static <T> T checkFound(Optional<T> resource, Long id) {
        if (!resource.isPresent()) {
            logger.error(Constants.ERROR_MESSAGE_NOT_FOUND_LOGGER, id);
            throw new ResourceNotFoundException(String.format(Constants.ERROR_MESSAGE_NOT_FOUND, id));
        }

        return resource.get();
    }

    /**
     * Método que se encarga de verificar si el email ingresado ya existe
     *
     * @param resource recurso a verificar
     * @param <T>      tipo del recurso a verificar
     */
    public static <T> void checkExistingEmail(Optional<T> resource) {
        if (resource.isPresent()) {
            logger.error(Constants.ERROR_MESSAGE_EXISTING_EMAIL);
            throw new GlobalException(Constants.ERROR_MESSAGE_EXISTING_EMAIL, HttpStatus.BAD_REQUEST);
        }

    }
}
