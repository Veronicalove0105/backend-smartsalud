package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.application.dto.HistorialClinicoRequest;
import com.policlinico.smartsalud.domain.entity.Cita;
import com.policlinico.smartsalud.domain.entity.HistorialClinico;
import com.policlinico.smartsalud.domain.repository.CitaRepository;
import com.policlinico.smartsalud.domain.repository.HistorialClinicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class HistorialClinicoService {

    private final HistorialClinicoRepository historialRepository;
    private final CitaRepository citaRepository;

    @Transactional
    public void registrarAtencion(HistorialClinicoRequest request, String emailMedico) {
        Cita cita = citaRepository.findById(request.citaId())
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));
        
        if (!cita.getMedico().getEmail().equals(emailMedico)) {
            throw new IllegalArgumentException("No tienes permiso para registrar atención en esta cita");
        }

        HistorialClinico historial = new HistorialClinico();
        historial.setPaciente(cita.getPaciente());
        historial.setMedico(cita.getMedico());
        historial.setCita(cita);
        historial.setFecha(LocalDate.now());
        historial.setMotivoConsulta(cita.getTipoConsulta());
        historial.setDiagnostico(request.diagnostico());
        historial.setTratamiento(request.tratamiento());
        historial.setObservaciones(request.observaciones());
        historial.setCreadoEn(LocalDateTime.now());

        historialRepository.save(historial);

        cita.setEstado("ATENDIDO");
        citaRepository.save(cita);
    }
}
