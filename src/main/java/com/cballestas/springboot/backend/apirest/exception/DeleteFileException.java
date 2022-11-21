package com.cballestas.springboot.backend.apirest.exception;

public class DeleteFileException extends RuntimeException {

    private static final long serialVersionUID = 2609268248180097353L;

    public DeleteFileException(String message) {
        super(message);
    }

    public DeleteFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
