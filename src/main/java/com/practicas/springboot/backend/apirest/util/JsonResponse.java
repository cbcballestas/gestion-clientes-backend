package com.practicas.springboot.backend.apirest.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResponse<T> {

	private String estado;
	private String mensaje;
	private T data;

}
