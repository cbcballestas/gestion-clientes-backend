package com.cballestas.springboot.backend.apirest.dto;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClienteDTO {
    Long id;

    String nombre;
    String apellido;
    String email;

    LocalDate createdAt;
}
