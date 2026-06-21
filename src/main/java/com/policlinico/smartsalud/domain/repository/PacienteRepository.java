package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Integer> {
    Optional<Paciente> findByEmail(String email);
    Optional<Paciente> findByDni(String dni);
    boolean existsByEmail(String email);
    boolean existsByDni(String dni);
}
