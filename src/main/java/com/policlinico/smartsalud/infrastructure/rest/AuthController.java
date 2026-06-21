package com.policlinico.smartsalud.infrastructure.rest;

import com.policlinico.smartsalud.application.dto.JwtResponse;
import com.policlinico.smartsalud.application.dto.LoginRequest;
import com.policlinico.smartsalud.application.dto.PerfilDTO;
import com.policlinico.smartsalud.application.dto.RegisterRequest;
import com.policlinico.smartsalud.application.dto.TokenRefreshRequest;
import com.policlinico.smartsalud.application.dto.TokenRefreshResponse;
import com.policlinico.smartsalud.application.service.AuthService;
import com.policlinico.smartsalud.application.service.TokenRefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final TokenRefreshService tokenRefreshService;

    // ── Login ─────────────────────────────────────────────────────────────────

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    /**
     * Endpoint de desarrollo: genera token sin validar contraseña.
     * ELIMINAR en producción.
     */
    @PostMapping("/dev-login")
    public ResponseEntity<JwtResponse> devLogin(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.devLogin(request));
    }

    // ── Register ──────────────────────────────────────────────────────────────

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    // ── Perfil del usuario autenticado ────────────────────────────────────────

    @GetMapping("/me")
    public ResponseEntity<PerfilDTO> getMe(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(authService.getMe(userDetails.getUsername()));
    }

    // ── Refresh token ─────────────────────────────────────────────────────────

    @PostMapping("/refresh")
    public ResponseEntity<TokenRefreshResponse> refresh(@RequestBody TokenRefreshRequest request) {
        String newAccessToken = tokenRefreshService.renovarAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(new TokenRefreshResponse(newAccessToken, request.getRefreshToken()));
    }

    // ── Logout ────────────────────────────────────────────────────────────────

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody TokenRefreshRequest request) {
        tokenRefreshService.revocarToken(request.getRefreshToken());
        return ResponseEntity.noContent().build();
    }
}