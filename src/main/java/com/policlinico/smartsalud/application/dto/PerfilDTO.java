package com.policlinico.smartsalud.application.dto;

import java.time.LocalDateTime;

public record PerfilDTO(
    Integer id,
    String nombres,
    String apellidos,
    String email,
    String dni,
    String telefono,
    String direccion,
    LocalDateTime fechaRegistro
) {}
