package com.cballestas.springboot.backend.apirest.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GlobalException extends RuntimeException {

    final HttpStatus status;

    public GlobalException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
