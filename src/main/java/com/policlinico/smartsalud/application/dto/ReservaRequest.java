package com.policlinico.smartsalud.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservaRequest {
    private Integer medicoId;
    private LocalDate fecha;
    private LocalTime hora;
    private String tipoConsulta; // PRESENCIAL, TELECONSULTA
    private String tarjetaNumero;
    private String cvv;
}
