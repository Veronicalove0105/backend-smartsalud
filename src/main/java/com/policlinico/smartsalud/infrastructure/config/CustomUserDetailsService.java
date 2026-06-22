package com.policlinico.smartsalud.infrastructure.config;

import com.policlinico.smartsalud.domain.entity.Paciente;
import com.policlinico.smartsalud.domain.entity.Medico;
import com.policlinico.smartsalud.domain.entity.UsuarioRol;
import com.policlinico.smartsalud.domain.repository.PacienteRepository;
import com.policlinico.smartsalud.domain.repository.MedicoRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;
    private final UsuarioRolRepository usuarioRolRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Paciente> pacienteOpt = pacienteRepository.findByEmail(username);
        
        if (pacienteOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();
            List<UsuarioRol> roles = usuarioRolRepository.findByEntidadAndEntidadId("PACIENTE", paciente.getId());
            
            List<GrantedAuthority> authorities = roles.stream()
                    .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getRol().getNombre().toUpperCase()))
                    .collect(Collectors.toList());

            if (authorities.isEmpty()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_PACIENTE"));
            }

            return new User(
                    paciente.getEmail(),
                    paciente.getPasswordHash() != null ? paciente.getPasswordHash() : "",
                    authorities
            );
        }

        Optional<Medico> medicoOpt = medicoRepository.findByEmail(username);
        if (medicoOpt.isPresent()) {
            Medico medico = medicoOpt.get();
            return new User(
                    medico.getEmail(),
                    medico.getPasswordHash() != null ? medico.getPasswordHash() : "",
                    List.of(new SimpleGrantedAuthority("ROLE_MEDICO"))
            );
        }

        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
}
