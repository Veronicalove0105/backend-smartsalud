package com.policlinico.smartsalud.application.dto;

public record MedicoDTO(
    Integer id,
    String nombres,
    String apellidos,
    String cmp,
    String dni,
    String email,
    String telefono,
    Integer especialidadId,
    String especialidadNombre,
    Short aniosExperiencia
) {}
