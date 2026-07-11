package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.application.dto.HistorialClinicoRequest;
import com.policlinico.smartsalud.domain.entity.Cita;
import com.policlinico.smartsalud.domain.entity.HistorialClinico;
import com.policlinico.smartsalud.domain.repository.CitaRepository;
import com.policlinico.smartsalud.domain.repository.HistorialClinicoRepository;
import com.policlinico.smartsalud.domain.repository.RecetaRepository;
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
    private final RecetaRepository recetaRepository;


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
        
        if (request.recetas() != null && !request.recetas().isEmpty()) {
            for (com.policlinico.smartsalud.application.dto.RecetaDTO dto : request.recetas()) {
                com.policlinico.smartsalud.domain.entity.Receta receta = new com.policlinico.smartsalud.domain.entity.Receta();
                receta.setCita(cita);
                receta.setPaciente(cita.getPaciente());
                receta.setMedico(cita.getMedico());
                receta.setMedicamentoNombre(dto.medicamentoNombre());
                receta.setDuracion(dto.duracion());
                receta.setInstrucciones(dto.instrucciones());
                receta.setNotas(dto.notas());
                receta.setManana(dto.manana() != null ? dto.manana() : false);
                receta.setTarde(dto.tarde() != null ? dto.tarde() : false);
                receta.setNoche(dto.noche() != null ? dto.noche() : false);
                receta.setFechaEmision(LocalDateTime.now());
                recetaRepository.save(receta);
            }
        }

        cita.setEstado("ATENDIDO");
        citaRepository.save(cita);
    }

    public com.policlinico.smartsalud.application.dto.HistorialClinicoDTO getHistorialPorCita(Integer citaId, String emailUser) {
        java.util.List<HistorialClinico> lista = historialRepository.findByCitaId(citaId);
        if (lista.isEmpty()) {
            throw new IllegalArgumentException("No hay documento clínico para esta cita");
        }
        HistorialClinico h = lista.get(0);
        
        // Verifica que el usuario que lo pide es el médico o el paciente
        if (!h.getMedico().getEmail().equals(emailUser) && !h.getPaciente().getEmail().equals(emailUser)) {
            throw new IllegalArgumentException("No tienes permiso para ver este documento");
        }

        return new com.policlinico.smartsalud.application.dto.HistorialClinicoDTO(
            h.getId(),
            h.getDiagnostico(),
            h.getTratamiento(),
            h.getObservaciones(),
            h.getFecha(),
            h.getCreadoEn(),
            h.getMedico().getNombres() + " " + h.getMedico().getApellidos(),
            h.getMedico().getEspecialidad().getNombre(),
            h.getPaciente().getNombres() + " " + h.getPaciente().getApellidos(),
            h.getPaciente().getDni()
        );
    }
}
