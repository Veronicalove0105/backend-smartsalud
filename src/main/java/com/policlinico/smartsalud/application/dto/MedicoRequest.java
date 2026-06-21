package com.policlinico.smartsalud.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record MedicoRequest(
    @NotBlank String dni,
    @NotBlank String nombres,
    @NotBlank String apellidos,
    @NotBlank String cmp,
    @NotNull Integer especialidadId,
    @Email String email,
    String telefono,
    String password,
    Short aniosExperiencia,
    List<Integer> sedesIds
) {}
