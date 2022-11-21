package com.cballestas.springboot.backend.apirest.exception;

public class UploadFileException extends RuntimeException {
    private static final long serialVersionUID = 2686745000644718433L;

    public UploadFileException(String message) {
        super(message);
    }

    public UploadFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
