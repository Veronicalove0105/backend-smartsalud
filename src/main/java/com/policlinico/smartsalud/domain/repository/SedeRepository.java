package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Integer> {
    List<Sede> findByActivaTrue();
}
