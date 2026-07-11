package com.policlinico.smartsalud.infrastructure.rest;

import com.policlinico.smartsalud.application.dto.RecetaDTO;
import com.policlinico.smartsalud.application.service.RecetaService;
import com.policlinico.smartsalud.domain.entity.Paciente;
import com.policlinico.smartsalud.domain.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recetas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RecetaController {

    private final RecetaService recetaService;
    private final PacienteRepository pacienteRepository;

    @GetMapping("/mis-recetas")
    @PreAuthorize("hasRole('PACIENTE')")
    public ResponseEntity<List<RecetaDTO>> getMisRecetas(Authentication authentication) {
        String email = authentication.getName();
        Paciente paciente = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));
        
        List<RecetaDTO> recetas = recetaService.getRecetasPorPaciente(paciente.getId());
        return ResponseEntity.ok(recetas);
    }

    @GetMapping("/cita/{citaId}")
    public ResponseEntity<List<RecetaDTO>> getRecetasPorCita(@PathVariable Integer citaId) {
        List<RecetaDTO> recetas = recetaService.getRecetasPorCita(citaId);
        return ResponseEntity.ok(recetas);
    }
}
