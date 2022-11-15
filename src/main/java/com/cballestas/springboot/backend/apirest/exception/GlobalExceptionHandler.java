package com.cballestas.springboot.backend.apirest.exception;

import com.cballestas.springboot.backend.apirest.dto.ErrorDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> manejarTodasLasExcepciones(GlobalException exception, WebRequest request) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(error, exception.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarResourceNotFoundException(ResourceNotFoundException exception,
                                                                          WebRequest request) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> manerarConstraintViolationException(DataIntegrityViolationException exception,
                                                                             WebRequest request) {

        String mensaje = NestedExceptionUtils.getMostSpecificCause(exception).getMessage();

        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), mensaje, request.getDescription(false));
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarErroresInternos(Exception exception, WebRequest request) {
        ErrorResponse error = new ErrorResponse(LocalDateTime.now(), exception.getMessage(),
                request.getDescription(false));
        return ResponseEntity.internalServerError().body(error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        // Se obtienen errores de los campos
        List<String> errores = ex.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());

        ErrorDTO errorDTO = new ErrorDTO(errores);

        return ResponseEntity.badRequest().body(errorDTO);

    }

}
