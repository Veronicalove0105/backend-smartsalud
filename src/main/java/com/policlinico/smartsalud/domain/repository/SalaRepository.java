package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaRepository extends JpaRepository<Sala, Integer> {

    List<Sala> findBySedeId(Integer sedeId);

    List<Sala> findBySedeIdAndActivaTrue(Integer sedeId);
}