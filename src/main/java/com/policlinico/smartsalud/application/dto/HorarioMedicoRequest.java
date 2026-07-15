package com.policlinico.smartsalud.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record HorarioMedicoRequest(
    Integer medicoId,
    Integer sedeId,
    Integer salaId,
    LocalDate fecha,
    LocalTime horaInicio,
    LocalTime horaFin,
    Short duracionSlot
) {}
