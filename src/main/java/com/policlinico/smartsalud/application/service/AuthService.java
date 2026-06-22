package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.application.dto.JwtResponse;
import com.policlinico.smartsalud.application.dto.LoginRequest;
import com.policlinico.smartsalud.application.dto.RegisterRequest;
import com.policlinico.smartsalud.application.dto.PerfilDTO;
import com.policlinico.smartsalud.domain.entity.Paciente;
import com.policlinico.smartsalud.domain.entity.Medico;
import com.policlinico.smartsalud.domain.repository.PacienteRepository;
import com.policlinico.smartsalud.domain.repository.MedicoRepository;
import com.policlinico.smartsalud.infrastructure.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String token = jwtUtil.generateToken(userDetails);
        
        Integer userId;
        String userName;

        Optional<Paciente> pacienteOpt = pacienteRepository.findByEmail(request.getEmail());
        if (pacienteOpt.isPresent()) {
            userId = pacienteOpt.get().getId();
            userName = pacienteOpt.get().getNombres() + " " + pacienteOpt.get().getApellidos();
        } else {
            Optional<Medico> medicoOpt = medicoRepository.findByEmail(request.getEmail());
            if (medicoOpt.isPresent()) {
                userId = medicoOpt.get().getId();
                userName = "Dr. " + medicoOpt.get().getNombres() + " " + medicoOpt.get().getApellidos();
            } else {
                throw new IllegalArgumentException("Usuario no encontrado en la base de datos");
            }
        }

        String role = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .filter(a -> a.startsWith("ROLE_"))
                .findFirst()
                .orElse("ROLE_PACIENTE");

        // Map Spring Security roles to frontend roles
        String frontendRole = switch(role) {
            case "ROLE_SUPER_ADMIN" -> "super_admin";
            case "ROLE_ADMIN" -> "admin";
            case "ROLE_RECEPCIONISTA" -> "receptionist";
            case "ROLE_MEDICO" -> "doctor";
            default -> "patient";
        };

        return JwtResponse.builder()
                .token(token)
                .type("Bearer")
                .id(userId)
                .email(request.getEmail())
                .nombre(userName)
                .role(frontendRole)
                .build();
    }

    public JwtResponse devLogin(LoginRequest request) {
        // Bypass authentication check, just generate token based on email pattern
        String email = request.getEmail();
        String frontendRole = "patient";
        String springRole = "ROLE_PACIENTE";
        Integer id = 1;
        String name = "Test User";

        if (email.contains("admin")) {
            frontendRole = "admin";
            springRole = "ROLE_ADMIN";
            name = "Administrador";
        } else if (email.contains("superadmin")) {
            frontendRole = "super_admin";
            springRole = "ROLE_SUPER_ADMIN";
            name = "Super Admin";
        } else if (email.contains("doctor") || email.contains("mendoza") || email.contains("medico")) {
            frontendRole = "doctor";
            springRole = "ROLE_MEDICO";
            name = "Dr. Carlos Mendoza";
            id = 1; // ID from seed data
        } else if (email.contains("recepcion")) {
            frontendRole = "receptionist";
            springRole = "ROLE_RECEPCIONISTA";
            name = "Recepción Principal";
        } else {
            frontendRole = "patient";
            springRole = "ROLE_PACIENTE";
            name = "Juan Perez";
        }

        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(email)
                .password("")
                .authorities(springRole)
                .build();

        String token = jwtUtil.generateToken(userDetails);

        return JwtResponse.builder()
                .token(token)
                .type("Bearer")
                .id(id)
                .email(email)
                .nombre(name)
                .role(frontendRole)
                .build();
    }

    public JwtResponse register(RegisterRequest request) {
        if (pacienteRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (pacienteRepository.existsByDni(request.getDni())) {
            throw new IllegalArgumentException("El DNI ya está registrado");
        }

        Paciente paciente = new Paciente();
        paciente.setNombres(request.getNombres());
        paciente.setApellidos(request.getApellidos());
        paciente.setEmail(request.getEmail());
        paciente.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        paciente.setDni(request.getDni());
        paciente.setTelefono(request.getTelefono());
        paciente.setActivo(true);
        paciente.setFechaRegistro(LocalDateTime.now());
        
        pacienteRepository.save(paciente);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(paciente.getEmail());
        final String token = jwtUtil.generateToken(userDetails);

        return JwtResponse.builder()
                .token(token)
                .type("Bearer")
                .id(paciente.getId())
                .email(paciente.getEmail())
                .nombre(paciente.getNombres() + " " + paciente.getApellidos())
                .role("patient") // Default for registration
                .build();
    }

    public PerfilDTO getMe(String email) {
        Paciente paciente = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return new PerfilDTO(
            paciente.getId(), paciente.getNombres(), paciente.getApellidos(),
            paciente.getEmail(), paciente.getDni(), paciente.getTelefono(),
            paciente.getDireccion(), paciente.getFechaRegistro()
        );
    }
}
