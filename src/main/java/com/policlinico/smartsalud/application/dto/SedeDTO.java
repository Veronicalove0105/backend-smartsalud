package com.policlinico.smartsalud.application.dto;

public record SedeDTO(
    Integer id,
    String nombre,
    String direccion,
    String distrito,
    String ciudad
) {}
