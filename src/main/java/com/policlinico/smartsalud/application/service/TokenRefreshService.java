package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.domain.entity.Medico;
import com.policlinico.smartsalud.domain.entity.Paciente;
import com.policlinico.smartsalud.domain.entity.TokenRefresh;
import com.policlinico.smartsalud.domain.repository.MedicoRepository;
import com.policlinico.smartsalud.domain.repository.PacienteRepository;
import com.policlinico.smartsalud.domain.repository.TokenRefreshRepository;
import com.policlinico.smartsalud.infrastructure.config.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenRefreshService {

    private static final long REFRESH_TOKEN_EXPIRATION_DAYS = 7;

    private final TokenRefreshRepository tokenRefreshRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    /**
     * Crea un refresh token para una entidad.
     * entidad: "paciente" | "medico"
     */
    @Transactional
    public TokenRefresh crearRefreshToken(Integer entidadId, String entidad) {
        tokenRefreshRepository.revocarTokensPorEntidad(entidadId, entidad);

        TokenRefresh refreshToken = TokenRefresh.builder()
                .entidad(entidad)
                .entidadId(entidadId)
                .token(UUID.randomUUID().toString())
                .expiraEn(LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRATION_DAYS))
                .revocado(false)
                .creadoEn(LocalDateTime.now())
                .build();

        return tokenRefreshRepository.save(refreshToken);
    }

    /**
     * Verifica que el refresh token sea válido, no revocado y no expirado.
     */
    public TokenRefresh verificarRefreshToken(String token) {
        TokenRefresh refreshToken = tokenRefreshRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token no encontrado"));

        if (Boolean.TRUE.equals(refreshToken.getRevocado())) {
            throw new IllegalArgumentException("Refresh token revocado");
        }

        if (refreshToken.getExpiraEn().isBefore(LocalDateTime.now())) {
            tokenRefreshRepository.delete(refreshToken);
            throw new IllegalArgumentException("Refresh token expirado. Inicia sesión nuevamente.");
        }

        return refreshToken;
    }

    /**
     * Genera un nuevo access token JWT a partir de un refresh token válido.
     */
    public String renovarAccessToken(String refreshTokenStr) {
        TokenRefresh refreshToken = verificarRefreshToken(refreshTokenStr);
        String email = resolverEmail(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return jwtUtil.generateToken(userDetails);
    }

    /**
     * Revoca un refresh token (logout).
     */
    @Transactional
    public void revocarToken(String token) {
        tokenRefreshRepository.findByToken(token).ifPresent(rt -> {
            rt.setRevocado(true);
            tokenRefreshRepository.save(rt);
        });
    }

    // ── helpers ──────────────────────────────────────────────────────────────

    /**
     * Resuelve el email del usuario según el tipo de entidad y su ID.
     */
    private String resolverEmail(TokenRefresh refreshToken) {
        return switch (refreshToken.getEntidad().toLowerCase()) {
            case "paciente" -> pacienteRepository.findById(refreshToken.getEntidadId())
                    .map(Paciente::getEmail)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Paciente no encontrado con id: " + refreshToken.getEntidadId()));

            case "medico" -> medicoRepository.findById(refreshToken.getEntidadId())
                    .map(Medico::getEmail)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Médico no encontrado con id: " + refreshToken.getEntidadId()));

            default -> throw new IllegalArgumentException(
                    "Tipo de entidad no soportado: " + refreshToken.getEntidad());
        };
    }
}