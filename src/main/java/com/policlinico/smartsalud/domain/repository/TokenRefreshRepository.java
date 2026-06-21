package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.TokenRefresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRefreshRepository extends JpaRepository<TokenRefresh, Integer> {

    Optional<TokenRefresh> findByToken(String token);

    @Modifying
    @Query("UPDATE TokenRefresh t SET t.revocado = true WHERE t.entidadId = :entidadId AND t.entidad = :entidad")
    void revocarTokensPorEntidad(Integer entidadId, String entidad);

    void deleteByToken(String token);
}
