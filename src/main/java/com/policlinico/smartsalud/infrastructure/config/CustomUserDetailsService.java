package com.policlinico.smartsalud.infrastructure.config;

import com.policlinico.smartsalud.domain.entity.Paciente;
import com.policlinico.smartsalud.domain.entity.UsuarioRol;
import com.policlinico.smartsalud.domain.repository.PacienteRepository;
import com.policlinico.smartsalud.domain.repository.UsuarioRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PacienteRepository pacienteRepository;
    private final UsuarioRolRepository usuarioRolRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Paciente paciente = pacienteRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        List<UsuarioRol> roles = usuarioRolRepository.findByEntidadAndEntidadId("PACIENTE", paciente.getId());
        
        List<GrantedAuthority> authorities = roles.stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getRol().getNombre().toUpperCase()))
                .collect(Collectors.toList());

        if (authorities.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_PACIENTE"));
        }

        return new User(
                paciente.getEmail(),
                paciente.getPasswordHash(),
                authorities
        );
    }
}
