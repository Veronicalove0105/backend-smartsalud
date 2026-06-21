package com.policlinico.smartsalud.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CitaUpdateRequest {
    @NotNull(message = "El médico es obligatorio")
    private Integer medicoId;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora es obligatoria")
    private LocalTime hora;
}
