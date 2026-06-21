package com.policlinico.smartsalud.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record HorarioMedicoDTO(
    Integer id,
    LocalDate fecha,
    LocalTime horaInicio,
    LocalTime horaFin,
    Boolean disponible
) {}
