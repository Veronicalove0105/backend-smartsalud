package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryPort extends JpaRepository<Paciente, Integer> {

    Optional<Paciente> findByEmail(String email);

    boolean existsByEmail(String email);
}