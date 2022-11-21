package com.cballestas.springboot.backend.apirest.dto;

import com.cballestas.springboot.backend.apirest.util.Constants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClienteDTO {
    Long id;

    @NotNull(message = "DEBE ingresar un nombre")
    @NotEmpty(message = "El campo nombre NO puede ser vacío")
    @Size(min = 4, max = 60, message = "El nombre debe tener entre 4 y 60 caracteres")
    String nombre;

    @NotNull(message = "DEBE ingresar un apellido")
    @NotEmpty(message = "El campo apellido NO puede ser vacío")
    String apellido;

    @NotNull(message = "DEBE ingresar un email")
    @NotEmpty(message = "El campo email NO puede ser vacío")
    @Email(regexp = Constants.PATTERN_EMAIL, message = "DEBE ingresar un email válido")
    String email;

    @NotNull(message = "DEBE ingresar una fecha de nacimiento")
    @NotEmpty(message = "El campo fecha de nacimiento NO puede ser vacío")
    String fechaNacimiento;

    String foto;
}
