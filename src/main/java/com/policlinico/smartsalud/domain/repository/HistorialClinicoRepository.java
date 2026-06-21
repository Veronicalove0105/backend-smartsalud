package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.HistorialClinico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialClinicoRepository extends JpaRepository<HistorialClinico, Integer> {

    List<HistorialClinico> findByPacienteIdOrderByFechaDesc(Integer pacienteId);

    List<HistorialClinico> findByCitaId(Integer citaId);
}