package com.policlinico.smartsalud.application.dto;

import java.time.LocalDateTime;

public record RecetaDTO(
    Integer id,
    Integer citaId,
    String medicamentoNombre,
    String duracion,
    String instrucciones,
    String notas,
    Boolean manana,
    Boolean tarde,
    Boolean noche,
    LocalDateTime fechaEmision,
    String medicoNombre,
    String pacienteNombre
) {}
