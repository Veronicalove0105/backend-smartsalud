package com.policlinico.smartsalud.application.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record CitaDTO(
    Integer id,
    String codigoReserva,
    LocalDate fecha,
    LocalTime hora,
    String estado,
    String tipoConsulta,
    String modalidad,
    String medicoNombre,
    String especialidadNombre,
    String sedeNombre
) {}
