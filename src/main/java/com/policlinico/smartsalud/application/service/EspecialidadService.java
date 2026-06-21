package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.domain.entity.Especialidad;
import com.policlinico.smartsalud.domain.repository.EspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EspecialidadService {
    private final EspecialidadRepository repository;

    public List<Especialidad> getEspecialidadesActivas() {
        return repository.findByActivaTrue();
    }
}
