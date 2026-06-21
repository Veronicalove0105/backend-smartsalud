package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.application.dto.ReservaRequest;
import com.policlinico.smartsalud.application.dto.HorarioMedicoDTO;
import com.policlinico.smartsalud.domain.entity.*;
import com.policlinico.smartsalud.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository repository;
    private final HorarioMedicoRepository horarioMedicoRepository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final PagoRepository pagoRepository;

    public List<Cita> getCitasPorPaciente(Integer pacienteId) {
        return repository.findByPacienteIdOrderByFechaDescHoraDesc(pacienteId);
    }

    public List<HorarioMedicoDTO> getDisponibles(Integer medicoId, LocalDate fecha) {
        return horarioMedicoRepository.findByMedicoIdAndFecha(medicoId, fecha)
                .stream()
                .filter(HorarioMedico::getDisponible)
                .map(h -> new HorarioMedicoDTO(
                        h.getId(), h.getFecha(), h.getHoraInicio(), h.getHoraFin(), h.getDisponible()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public String reservar(String emailPaciente, ReservaRequest request) {
        Paciente paciente = pacienteRepository.findByEmail(emailPaciente)
                .orElseThrow(() -> new IllegalArgumentException("Paciente no encontrado"));

        Medico medico = medicoRepository.findById(request.getMedicoId())
                .orElseThrow(() -> new IllegalArgumentException("Médico no encontrado"));

        List<HorarioMedico> horarios = horarioMedicoRepository.findByMedicoIdAndFecha(medico.getId(), request.getFecha());
        HorarioMedico horario = horarios.stream()
                .filter(h -> h.getHoraInicio().equals(request.getHora()) && h.getDisponible())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("El horario no está disponible"));

        // Validar pago de forma estricta (simulado pero requerido)
        if (request.getTarjetaNumero() == null || request.getTarjetaNumero().length() < 15) {
            throw new IllegalArgumentException("Tarjeta de crédito inválida");
        }
        if (request.getCvv() == null || request.getCvv().length() < 3) {
            throw new IllegalArgumentException("CVV inválido");
        }

        // Crear cita
        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setSede(horario.getSede());
        cita.setHorario(horario);
        cita.setFecha(request.getFecha());
        cita.setHora(request.getHora());
        cita.setEstado("RESERVADO");
        cita.setTipoConsulta(request.getTipoConsulta() != null ? request.getTipoConsulta() : "PRIMERA_VEZ");
        cita.setModalidad("PRESENCIAL");
        cita.setCodigoReserva("RES-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        cita.setFechaCreacion(LocalDateTime.now());
        repository.save(cita);

        // Actualizar horario
        horario.setDisponible(false);
        horarioMedicoRepository.save(horario);

        // Crear pago transaccional
        Pago pago = new Pago();
        pago.setCita(cita);
        pago.setMonto(new BigDecimal("150.00")); // Precio base simulado
        pago.setMetodo("TARJETA_CREDITO");
        pago.setEstado("COMPLETADO");
        pago.setReferenciaExterna("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        pago.setFecha(LocalDateTime.now());
        pagoRepository.save(pago);

        return cita.getCodigoReserva();
    }
}
