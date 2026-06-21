package com.policlinico.smartsalud.domain.repository;

import com.policlinico.smartsalud.domain.entity.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Integer> {
    List<UsuarioRol> findByEntidadAndEntidadId(String entidad, Integer entidadId);
}
