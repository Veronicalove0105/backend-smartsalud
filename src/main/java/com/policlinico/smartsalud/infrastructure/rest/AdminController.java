package com.policlinico.smartsalud.infrastructure.rest;

import com.policlinico.smartsalud.application.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.List;
import jakarta.validation.Valid;
import com.policlinico.smartsalud.application.dto.UsuarioDTO;
import com.policlinico.smartsalud.application.dto.UsuarioRequest;
import com.policlinico.smartsalud.application.dto.PacienteDTO;
import com.policlinico.smartsalud.application.dto.PacienteRequest;
import com.policlinico.smartsalud.application.dto.HorarioMedicoDTO;
import com.policlinico.smartsalud.application.dto.HorarioMedicoRequest;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/reportes")
    public ResponseEntity<Map<String, Object>> getReportes() {
        return ResponseEntity.ok(adminService.getReportes());
    }

    // Mantenimiento de Usuarios Administrativos
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDTO>> getUsuariosAdministrativos() {
        return ResponseEntity.ok(adminService.getUsuariosAdministrativos());
    }

    @GetMapping("/pacientes")
    public ResponseEntity<List<PacienteDTO>> getPacientes() {
        return ResponseEntity.ok(adminService.getAllPacientes());
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioDTO> createUsuario(@Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(adminService.createUsuario(request));
    }

    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Integer id) {
        adminService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Integer id,
            @Valid @RequestBody UsuarioRequest request) {
        return ResponseEntity.ok(adminService.updateUsuario(id, request));
    }

    @DeleteMapping("/pacientes/{id}")
    public ResponseEntity<Void> deletePaciente(@PathVariable Integer id) {
        adminService.deletePaciente(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/pacientes/{id}")
    public ResponseEntity<PacienteDTO> updatePaciente(@PathVariable Integer id,
            @Valid @RequestBody PacienteRequest request) {
        return ResponseEntity.ok(adminService.updatePaciente(id, request));
    }

    // Mantenimiento de Horarios
    @GetMapping("/horarios")
    public ResponseEntity<List<HorarioMedicoDTO>> getHorarios() {
        return ResponseEntity.ok(adminService.getAllHorarios());
    }

    @PostMapping("/horarios")
    public ResponseEntity<HorarioMedicoDTO> createHorario(@Valid @RequestBody HorarioMedicoRequest request) {
        return ResponseEntity.ok(adminService.createHorario(request));
    }

    @PutMapping("/horarios/{id}")
    public ResponseEntity<HorarioMedicoDTO> updateHorario(@PathVariable Integer id,
            @Valid @RequestBody HorarioMedicoRequest request) {
        return ResponseEntity.ok(adminService.updateHorario(id, request));
    }

    @DeleteMapping("/horarios/{id}")
    public ResponseEntity<Void> deleteHorario(@PathVariable Integer id) {
        adminService.deleteHorario(id);
        return ResponseEntity.noContent().build();
    }
}
