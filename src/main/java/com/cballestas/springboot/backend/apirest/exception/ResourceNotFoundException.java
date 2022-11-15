package com.cballestas.springboot.backend.apirest.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -6684064902049098958L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
