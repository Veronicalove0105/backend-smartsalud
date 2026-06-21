package com.policlinico.smartsalud.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SedeRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    private String distrito;
    
    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    private String telefono;
    private String email;
}
