package com.policlinico.smartsalud.infrastructure.rest;

import com.policlinico.smartsalud.application.dto.HistorialClinicoRequest;
import com.policlinico.smartsalud.application.service.HistorialClinicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/historial")
@RequiredArgsConstructor
public class HistorialClinicoController {

    private final HistorialClinicoService service;

    @PostMapping
    public ResponseEntity<Void> registrarAtencion(@Valid @RequestBody HistorialClinicoRequest request, Authentication authentication) {
        service.registrarAtencion(request, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cita/{citaId}")
    public ResponseEntity<com.policlinico.smartsalud.application.dto.HistorialClinicoDTO> getHistorialPorCita(
            @PathVariable Integer citaId, Authentication authentication) {
        return ResponseEntity.ok(service.getHistorialPorCita(citaId, authentication.getName()));
    }
}
