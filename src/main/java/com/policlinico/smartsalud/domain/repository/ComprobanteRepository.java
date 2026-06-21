package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComprobanteRepository extends JpaRepository<Comprobante, Integer> {

    List<Comprobante> findByPagoId(Integer pagoId);

    Optional<Comprobante> findByNumero(String numero);
}