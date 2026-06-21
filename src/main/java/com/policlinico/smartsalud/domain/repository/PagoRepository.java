package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    Optional<Pago> findByCitaId(Integer citaId);
}
