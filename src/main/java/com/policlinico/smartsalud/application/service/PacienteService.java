package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.application.dto.CitaDTO;
import com.policlinico.smartsalud.application.dto.PerfilDTO;
import com.policlinico.smartsalud.application.dto.PerfilUpdateRequest;
import com.policlinico.smartsalud.domain.entity.Paciente;
import com.policlinico.smartsalud.domain.repository.CitaRepository;
import com.policlinico.smartsalud.domain.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;
    private final CitaRepository citaRepository;

    public PerfilDTO updatePerfil(String email, PerfilUpdateRequest request) {
        Paciente paciente = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        if (request.getTelefono() != null) paciente.setTelefono(request.getTelefono());
        if (request.getDireccion() != null) paciente.setDireccion(request.getDireccion());

        pacienteRepository.save(paciente);

        return new PerfilDTO(
                paciente.getId(), paciente.getNombres(), paciente.getApellidos(),
                paciente.getEmail(), paciente.getDni(), paciente.getTelefono(),
                paciente.getDireccion(), paciente.getFechaRegistro()
        );
    }

    public List<CitaDTO> getHistorial(String email) {
        Paciente paciente = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        return citaRepository.findByPacienteIdOrderByFechaDescHoraDesc(paciente.getId())
                .stream()
                .map(cita -> new CitaDTO(
                        cita.getId(),
                        cita.getCodigoReserva(),
                        cita.getFecha(),
                        cita.getHora(),
                        cita.getEstado(),
                        cita.getTipoConsulta(),
                        cita.getModalidad(),
                        cita.getMedico().getNombres() + " " + cita.getMedico().getApellidos(),
                        cita.getMedico().getEspecialidad().getNombre(),
                        cita.getSede().getNombre()
                ))
                .collect(Collectors.toList());
    }
}
