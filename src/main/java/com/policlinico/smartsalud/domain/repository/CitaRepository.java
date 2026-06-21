package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {
    List<Cita> findByPacienteIdOrderByFechaDescHoraDesc(Integer pacienteId);
    List<Cita> findByMedicoIdAndFechaOrderByHoraAsc(Integer medicoId, LocalDate fecha);
    List<Cita> findBySedeIdAndFecha(Integer sedeId, LocalDate fecha);
}
