package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.application.dto.CitaDTO;
import com.policlinico.smartsalud.application.dto.HorarioMedicoDTO;
import com.policlinico.smartsalud.domain.entity.Medico;
import com.policlinico.smartsalud.domain.repository.CitaRepository;
import com.policlinico.smartsalud.domain.repository.HorarioMedicoRepository;
import com.policlinico.smartsalud.domain.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.policlinico.smartsalud.domain.entity.Paciente;
import com.policlinico.smartsalud.domain.entity.UsuarioRol;
import com.policlinico.smartsalud.domain.entity.Rol;
import com.policlinico.smartsalud.domain.repository.PacienteRepository;
import com.policlinico.smartsalud.domain.repository.UsuarioRolRepository;
import com.policlinico.smartsalud.domain.repository.RolRepository;
import com.policlinico.smartsalud.application.dto.MedicoRequest;
import com.policlinico.smartsalud.application.dto.MedicoDTO;

@Service
@RequiredArgsConstructor
public class MedicoService {
    private final MedicoRepository repository;
    private final HorarioMedicoRepository horarioMedicoRepository;
    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final RolRepository rolRepository;
    private final com.policlinico.smartsalud.domain.repository.EspecialidadRepository especialidadRepository;
    private final com.policlinico.smartsalud.domain.repository.SedeRepository sedeRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Medico> getMedicosPorEspecialidad(Integer especialidadId) {
        return repository.findByEspecialidadIdAndActivoTrue(especialidadId);
    }

    public List<Medico> getAllMedicos() {
        return repository.findAll();
    }

    public List<HorarioMedicoDTO> getAgenda(String email) {
        Medico medico = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado"));

        return horarioMedicoRepository.findByMedicoIdAndFechaGreaterThanEqualOrderByFechaAsc(medico.getId(), LocalDate.now())
                .stream()
                .map(h -> new HorarioMedicoDTO(
                        h.getId(), h.getFecha(), h.getHoraInicio(), h.getHoraFin(), h.getDisponible()
                ))
                .collect(Collectors.toList());
    }

    public List<CitaDTO> getPacientesDeHoy(String email) {
        Medico medico = repository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado"));

        return citaRepository.findByMedicoIdAndFechaOrderByHoraAsc(medico.getId(), LocalDate.now())
                .stream()
                .map(cita -> new CitaDTO(
                        cita.getId(),
                        cita.getCodigoReserva(),
                        cita.getFecha(),
                        cita.getHora(),
                        cita.getEstado(),
                        cita.getTipoConsulta(),
                        cita.getModalidad(),
                        medico.getNombres() + " " + medico.getApellidos(),
                        medico.getEspecialidad().getNombre(),
                        cita.getSede().getNombre(),
                        cita.getPaciente().getNombres() + " " + cita.getPaciente().getApellidos()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public MedicoDTO createMedico(MedicoRequest request) {
        if (request.password() == null || request.password().trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria para nuevos médicos");
        }
        if (repository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        // Create Medico profile
        Medico medico = new Medico();
        medico.setDni(request.dni());
        medico.setNombres(request.nombres());
        medico.setApellidos(request.apellidos());
        medico.setCmp(request.cmp());
        medico.setEmail(request.email());
        medico.setTelefono(request.telefono());
        medico.setPasswordHash(passwordEncoder.encode(request.password()));
        medico.setAniosExperiencia(request.aniosExperiencia() != null ? request.aniosExperiencia() : 0);
        medico.setEspecialidad(especialidadRepository.findById(request.especialidadId()).orElseThrow());
        medico.setActivo(true);
        medico.setFechaIngreso(LocalDate.now());

        // Associate with Sedes
        if (request.sedesIds() != null && !request.sedesIds().isEmpty()) {
            // Note: The Medico entity should have a Set<Sede> sedes. We need to check if it has it.
        }

        medico = repository.save(medico);

        return new MedicoDTO(
            medico.getId(), medico.getNombres(), medico.getApellidos(), medico.getCmp(), 
            medico.getDni(), medico.getEmail(), medico.getTelefono(),
            medico.getEspecialidad().getId(), medico.getEspecialidad().getNombre(), medico.getAniosExperiencia()
        );
    }

    @Transactional
    public void deleteMedico(Integer id) {
        Medico m = repository.findById(id).orElseThrow();
        m.setActivo(false);
        repository.save(m);
    }

    @Transactional
    public MedicoDTO updateMedico(Integer id, MedicoRequest request) {
        Medico medico = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado"));

        medico.setNombres(request.nombres());
        medico.setApellidos(request.apellidos());
        medico.setEmail(request.email());
        medico.setDni(request.dni());
        medico.setTelefono(request.telefono());
        medico.setCmp(request.cmp());
        medico.setAniosExperiencia(request.aniosExperiencia() != null ? request.aniosExperiencia() : 0);
        medico.setEspecialidad(especialidadRepository.findById(request.especialidadId()).orElseThrow());
        
        if (request.password() != null && !request.password().trim().isEmpty()) {
            medico.setPasswordHash(passwordEncoder.encode(request.password()));
        }

        medico = repository.save(medico);

        return new MedicoDTO(
            medico.getId(), medico.getNombres(), medico.getApellidos(), medico.getCmp(), 
            medico.getDni(), medico.getEmail(), medico.getTelefono(),
            medico.getEspecialidad().getId(), medico.getEspecialidad().getNombre(), medico.getAniosExperiencia()
        );
    }
}
