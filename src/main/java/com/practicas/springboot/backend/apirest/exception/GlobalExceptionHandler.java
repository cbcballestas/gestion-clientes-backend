package com.practicas.springboot.backend.apirest.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> manejarResourceNotFoundException(ResourceNotFoundException ex,
			WebRequest request) {
		ErrorResponse error = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ErrorResponse> manejarGlobalException(GlobalException ex, WebRequest request) {
		ErrorResponse error = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(error, ex.getEstado());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> manejarTodasLasExcepciones(Exception ex, WebRequest request) {
		ErrorResponse error = new ErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errores = new HashMap<>();

		// Se recorren errores obtenidos en las validaciones de los campos y se
		// almacenan en una única respuesta
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String campo = ((FieldError) error).getField();
			String mensaje = error.getDefaultMessage();

			errores.put(campo, mensaje);
		});

		return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);

	}

}
