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
}
