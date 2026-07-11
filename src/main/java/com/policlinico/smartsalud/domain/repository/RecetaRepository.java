package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Integer> {
    List<Receta> findByCitaId(Integer citaId);
    List<Receta> findByPacienteId(Integer pacienteId);
    List<Receta> findByMedicoId(Integer medicoId);
}
