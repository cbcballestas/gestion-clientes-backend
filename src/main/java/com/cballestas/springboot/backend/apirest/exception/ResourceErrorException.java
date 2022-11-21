package com.cballestas.springboot.backend.apirest.exception;

public class ResourceErrorException extends RuntimeException {
    private static final long serialVersionUID = 7629725402403542520L;

    public ResourceErrorException(String message) {
        super(message);
    }
}
