package com.policlinico.smartsalud.application.dto;

public record UsuarioDTO(
        Integer id,
        String dni,
        String nombres,
        String apellidos,
        String email,
        String telefono,
        String rolNombre) {
}