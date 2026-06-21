package com.policlinico.smartsalud.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EspecialidadRequest {
    @NotBlank(message = "El nombre de la especialidad es obligatorio")
    private String nombre;
    
    private String descripcion;
    private String iconoUrl;
}
