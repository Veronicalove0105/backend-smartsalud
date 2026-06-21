package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.domain.repository.CitaRepository;
import com.policlinico.smartsalud.domain.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDateTime;

import com.policlinico.smartsalud.domain.repository.PacienteRepository;
import com.policlinico.smartsalud.domain.repository.UsuarioRolRepository;
import com.policlinico.smartsalud.domain.repository.RolRepository;
import com.policlinico.smartsalud.domain.entity.Paciente;
import com.policlinico.smartsalud.domain.entity.UsuarioRol;
import com.policlinico.smartsalud.domain.entity.Rol;
import com.policlinico.smartsalud.application.dto.UsuarioDTO;
import com.policlinico.smartsalud.application.dto.UsuarioRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final CitaRepository citaRepository;
    private final PagoRepository pagoRepository;
    private final PacienteRepository pacienteRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public Map<String, Object> getReportes() {
        Map<String, Object> reportes = new HashMap<>();
        
        long totalCitas = citaRepository.count();
        reportes.put("total_citas", totalCitas);

        // Calculate total earnings
        BigDecimal ganancias = pagoRepository.findAll().stream()
                .filter(p -> "COMPLETADO".equals(p.getEstado()))
                .map(p -> p.getMonto())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                
        reportes.put("ganancias_totales", ganancias);

        return reportes;
    }

    public List<UsuarioDTO> getUsuariosAdministrativos() {
        return pacienteRepository.findAll().stream()
            .map(p -> {
                List<UsuarioRol> roles = usuarioRolRepository.findByEntidadAndEntidadId("PACIENTE", p.getId());
                String rolNombre = roles.isEmpty() ? "PACIENTE" : roles.get(0).getRol().getNombre();
                return new UsuarioDTO(p.getId(), p.getDni(), p.getNombres(), p.getApellidos(), p.getEmail(), p.getTelefono(), rolNombre);
            })
            .filter(u -> !"PACIENTE".equals(u.rolNombre()) && !"MEDICO".equals(u.rolNombre()))
            .collect(Collectors.toList());
    }

    @Transactional
    public UsuarioDTO createUsuario(UsuarioRequest request) {
        if (request.password() == null || request.password().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria para nuevos usuarios");
        }
        if (pacienteRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        Paciente paciente = new Paciente();
        paciente.setNombres(request.nombres());
        paciente.setApellidos(request.apellidos());
        paciente.setEmail(request.email());
        paciente.setPasswordHash(passwordEncoder.encode(request.password()));
        paciente.setDni(request.dni());
        paciente.setTelefono(request.telefono());
        paciente.setActivo(true);
        paciente.setFechaRegistro(LocalDateTime.now());
        
        paciente = pacienteRepository.save(paciente);

        Rol rol = rolRepository.findByNombre(request.rolNombre())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setEntidad("PACIENTE");
        usuarioRol.setEntidadId(paciente.getId());
        usuarioRol.setRol(rol);
        usuarioRolRepository.save(usuarioRol);

        return new UsuarioDTO(paciente.getId(), paciente.getDni(), paciente.getNombres(), paciente.getApellidos(), paciente.getEmail(), paciente.getTelefono(), rol.getNombre());
    }

    @Transactional
    public void deleteUsuario(Integer id) {
        Paciente p = pacienteRepository.findById(id).orElseThrow();
        p.setActivo(false);
        pacienteRepository.save(p);
    }

    @Transactional
    public UsuarioDTO updateUsuario(Integer id, UsuarioRequest request) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        
        paciente.setNombres(request.nombres());
        paciente.setApellidos(request.apellidos());
        paciente.setEmail(request.email());
        paciente.setDni(request.dni());
        paciente.setTelefono(request.telefono());

        if (request.password() != null && !request.password().trim().isEmpty()) {
            paciente.setPasswordHash(passwordEncoder.encode(request.password()));
        }

        paciente = pacienteRepository.save(paciente);

        Rol rol = rolRepository.findByNombre(request.rolNombre())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        List<UsuarioRol> roles = usuarioRolRepository.findByEntidadAndEntidadId("PACIENTE", paciente.getId());
        if (!roles.isEmpty()) {
            UsuarioRol usuarioRol = roles.get(0);
            usuarioRol.setRol(rol);
            usuarioRolRepository.save(usuarioRol);
        } else {
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setEntidad("PACIENTE");
            usuarioRol.setEntidadId(paciente.getId());
            usuarioRol.setRol(rol);
            usuarioRolRepository.save(usuarioRol);
        }

        return new UsuarioDTO(paciente.getId(), paciente.getDni(), paciente.getNombres(), paciente.getApellidos(), paciente.getEmail(), paciente.getTelefono(), rol.getNombre());
    }
}
