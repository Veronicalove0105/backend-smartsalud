package com.policlinico.smartsalud.infrastructure.rest;

import com.policlinico.smartsalud.application.dto.EspecialidadDTO;
import com.policlinico.smartsalud.application.service.EspecialidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {

    private final EspecialidadService service;

    @GetMapping
    public ResponseEntity<List<EspecialidadDTO>> getEspecialidades() {
        List<EspecialidadDTO> list = service.getEspecialidadesActivas().stream()
            .map(e -> new EspecialidadDTO(e.getId(), e.getNombre(), e.getDescripcion(), e.getIconoUrl()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }
}
