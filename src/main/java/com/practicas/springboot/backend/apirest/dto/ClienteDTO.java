package com.practicas.springboot.backend.apirest.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

	private Long id;

	@NotBlank(message = "DEBE ingresar un nombre")
	@NotEmpty(message = "DEBE ingresar un nombre")
	@Size(min = 3, message = "El nombre DEBE tener al menos 3 caracteres")
	private String nombre;

	@NotBlank(message = "DEBE ingresar un apellido")
	@NotEmpty(message = "DEBE ingresar un apellido")
	@Size(min = 4, message = "El apellido DEBE tener al menos 4 caracteres")
	private String apellido;

	@NotBlank(message = "DEBE ingresar un email")
	@NotEmpty(message = "DEBE ingresar un email")
	@Email(message = "DEBE ingresar un email válido", regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
	private String email;

	private Date createAt;

}
