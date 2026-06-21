package com.policlinico.smartsalud.infrastructure.rest;

import com.policlinico.smartsalud.application.dto.CitaDTO;
import com.policlinico.smartsalud.application.dto.PerfilDTO;
import com.policlinico.smartsalud.application.dto.PerfilUpdateRequest;
import com.policlinico.smartsalud.application.service.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paciente")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PutMapping("/perfil")
    public ResponseEntity<PerfilDTO> updatePerfil(@RequestBody PerfilUpdateRequest request, Authentication authentication) {
        return ResponseEntity.ok(pacienteService.updatePerfil(authentication.getName(), request));
    }

    @GetMapping("/historial")
    public ResponseEntity<List<CitaDTO>> getHistorial(Authentication authentication) {
        return ResponseEntity.ok(pacienteService.getHistorial(authentication.getName()));
    }
}
