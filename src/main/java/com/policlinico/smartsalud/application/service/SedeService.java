package com.policlinico.smartsalud.application.service;

import com.policlinico.smartsalud.domain.entity.Sede;
import com.policlinico.smartsalud.domain.repository.SedeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SedeService {
    private final SedeRepository repository;

    public List<Sede> getSedesActivas() {
        return repository.findByActivaTrue();
    }
}
