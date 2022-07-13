package com.practicas.springboot.backend.apirest.util;

import org.springframework.stereotype.Component;

@Component
public class ConstantsUtil {

	// Status
	public final String STATUS_SUCCESS = "OK";
	public final String STATUS_ERROR = "ERROR";

	// Messages
	public final String MESSAGE_SUCCESS = "Operación Éxitosa";
	public final String MESSAGE_ERROR = "Error al realizar petición";

	// Pagination info
	public static final String NUMERO_PAGINA = "0";
	public static final String MEDIDA_PAGINA = "10";
	public static final String ORDER_BY = "id";
	public static final String SORT_DIR = "ASC";

}
