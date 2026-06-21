package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {
    Optional<Medico> findByDni(String dni);
    Optional<Medico> findByCmp(String cmp);
    Optional<Medico> findByEmail(String email);
    List<Medico> findByEspecialidadIdAndActivoTrue(Integer especialidadId);
}
