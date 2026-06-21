package com.policlinico.smartsalud.infrastructure.rest;

import com.policlinico.smartsalud.application.dto.HorarioMedicoDTO;
import com.policlinico.smartsalud.application.dto.ReservaRequest;
import com.policlinico.smartsalud.application.service.CitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/citas")
@RequiredArgsConstructor
public class CitaController {

    private final CitaService citaService;

    @GetMapping("/disponibles")
    public ResponseEntity<List<HorarioMedicoDTO>> getDisponibles(
            @RequestParam Integer medicoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(citaService.getDisponibles(medicoId, fecha));
    }

    @PostMapping("/reservar")
    public ResponseEntity<String> reservar(@RequestBody ReservaRequest request, Authentication authentication) {
        String codigoReserva = citaService.reservar(authentication.getName(), request);
        return ResponseEntity.ok(codigoReserva);
    }
}
