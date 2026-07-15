package com.policlinico.smartsalud.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PacienteDTO(
    Integer id,
    String nombres,
    String apellidos,
    String email,
    String dni,
    String telefono,
    String direccion,
    String sexo,
    LocalDate fechaNacimiento,
    Boolean activo,
    LocalDateTime fechaRegistro
) {}
