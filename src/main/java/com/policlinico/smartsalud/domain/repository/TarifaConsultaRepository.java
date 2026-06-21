package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.TarifaConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TarifaConsultaRepository extends JpaRepository<TarifaConsulta, Integer> {

    List<TarifaConsulta> findByMedicoEspecialidadId(Integer especialidadId);

    Optional<TarifaConsulta> findByMedicoEspecialidadIdAndActivaTrue(Integer especialidadId);
}