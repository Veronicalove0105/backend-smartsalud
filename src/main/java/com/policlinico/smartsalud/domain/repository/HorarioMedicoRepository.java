package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.HorarioMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HorarioMedicoRepository extends JpaRepository<HorarioMedico, Integer> {
    List<HorarioMedico> findByMedicoIdAndFechaGreaterThanEqualOrderByFechaAsc(Integer medicoId, LocalDate fecha);
    List<HorarioMedico> findByMedicoIdAndFecha(Integer medicoId, LocalDate fecha);
}
