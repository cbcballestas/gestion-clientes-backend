package com.practicas.springboot.backend.apirest.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GlobalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private HttpStatus estado;

	public GlobalException(String message, HttpStatus estado) {
		super(message);
		this.estado = estado;
	}

}
