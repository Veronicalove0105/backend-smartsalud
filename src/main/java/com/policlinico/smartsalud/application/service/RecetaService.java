package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.application.dto.RecetaDTO;
import com.policlinico.smartsalud.domain.entity.Receta;
import com.policlinico.smartsalud.domain.repository.RecetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecetaService {

    private final RecetaRepository recetaRepository;

    public List<RecetaDTO> getRecetasPorPaciente(Integer pacienteId) {
        return recetaRepository.findByPacienteId(pacienteId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<RecetaDTO> getRecetasPorCita(Integer citaId) {
        return recetaRepository.findByCitaId(citaId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private RecetaDTO mapToDTO(Receta r) {
        return new RecetaDTO(
                r.getId(),
                r.getCita().getId(),
                r.getMedicamentoNombre(),
                r.getDuracion(),
                r.getInstrucciones(),
                r.getNotas(),
                r.getManana(),
                r.getTarde(),
                r.getNoche(),
                r.getFechaEmision(),
                r.getMedico().getNombres() + " " + r.getMedico().getApellidos(),
                r.getPaciente().getNombres() + " " + r.getPaciente().getApellidos()
        );
    }
}
