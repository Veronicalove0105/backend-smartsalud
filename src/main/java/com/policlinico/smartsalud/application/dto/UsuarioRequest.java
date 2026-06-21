package com.policlinico.smartsalud.application.dto;

public record UsuarioRequest(
        String dni,
        String nombres,
        String apellidos,
        String email,
        String telefono,
        String password,
        String rolNombre) {
}