package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.PlantillaHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantillaHorarioRepository extends JpaRepository<PlantillaHorario, Integer> {

    List<PlantillaHorario> findByMedicoId(Integer medicoId);

    List<PlantillaHorario> findByMedicoIdAndActivaTrue(Integer medicoId);
}