package com.policlinico.smartsalud.infrastructure.rest;

import com.policlinico.smartsalud.application.dto.CitaDTO;
import com.policlinico.smartsalud.application.dto.HorarioMedicoDTO;
import com.policlinico.smartsalud.application.dto.MedicoDTO;
import com.policlinico.smartsalud.application.dto.MedicoRequest;
import com.policlinico.smartsalud.application.service.MedicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/medicos")
@RequiredArgsConstructor
public class MedicoController {

    private final MedicoService service;

    @GetMapping
    public ResponseEntity<List<MedicoDTO>> getAll() {
        List<MedicoDTO> list = service.getAllMedicos().stream()
            .map(m -> new MedicoDTO(
                m.getId(), m.getNombres(), m.getApellidos(), m.getCmp(),
                m.getDni(), m.getEmail(), m.getTelefono(),
                m.getEspecialidad().getId(), m.getEspecialidad().getNombre(),
                m.getAniosExperiencia()
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/especialidad/{id}")
    public ResponseEntity<List<MedicoDTO>> getByEspecialidad(@PathVariable Integer id) {
        List<MedicoDTO> list = service.getMedicosPorEspecialidad(id).stream()
            .map(m -> new MedicoDTO(
                m.getId(), m.getNombres(), m.getApellidos(), m.getCmp(),
                m.getDni(), m.getEmail(), m.getTelefono(),
                m.getEspecialidad().getId(), m.getEspecialidad().getNombre(),
                m.getAniosExperiencia()
            ))
            .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/agenda")
    public ResponseEntity<List<HorarioMedicoDTO>> getAgenda(Authentication authentication) {
        return ResponseEntity.ok(service.getAgenda(authentication.getName()));
    }

    @GetMapping("/pacientes")
    public ResponseEntity<List<CitaDTO>> getPacientesDeHoy(Authentication authentication) {
        return ResponseEntity.ok(service.getPacientesDeHoy(authentication.getName()));
    }

    @PostMapping
    public ResponseEntity<MedicoDTO> createMedico(@Valid @RequestBody MedicoRequest request) {
        return ResponseEntity.ok(service.createMedico(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedico(@PathVariable Integer id) {
        service.deleteMedico(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicoDTO> updateMedico(@PathVariable Integer id, @Valid @RequestBody MedicoRequest request) {
        return ResponseEntity.ok(service.updateMedico(id, request));
    }
}
