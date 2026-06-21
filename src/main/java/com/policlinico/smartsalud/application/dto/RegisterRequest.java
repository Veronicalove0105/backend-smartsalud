package com.policlinico.smartsalud.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String nombres;
    private String apellidos;
    private String email;
    private String password;
    private String dni;
    private String telefono;
}
