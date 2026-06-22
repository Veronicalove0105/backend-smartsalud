package com.policlinico.smartsalud.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HistorialClinicoRequest(
    @NotNull(message = "El ID de la cita es requerido")
    Integer citaId,
    
    @NotBlank(message = "El diagnóstico es requerido")
    String diagnostico,
    
    String tratamiento,
    String observaciones
) {}
