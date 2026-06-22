package com.policlinico.smartsalud.application.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record HistorialClinicoDTO(
    Integer id,
    String diagnostico,
    String tratamiento,
    String observaciones,
    LocalDate fecha,
    LocalDateTime creadoEn,
    String medicoNombre,
    String especialidadNombre,
    String pacienteNombre,
    String dniPaciente
) {
}
