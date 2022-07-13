package com.practicas.springboot.backend.apirest.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

	private LocalDateTime fecha;
	private String mensaje;
	private String urlRecurso;

}
