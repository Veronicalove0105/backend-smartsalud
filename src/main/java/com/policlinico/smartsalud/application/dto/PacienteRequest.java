package com.policlinico.smartsalud.application.dto;

import java.time.LocalDate;

public record PacienteRequest(
        String dni,
        String nombres,
        String apellidos,
        String email,
        String telefono,
        String password,
        String direccion,
        String sexo,
        LocalDate fechaNacimiento) {
}
