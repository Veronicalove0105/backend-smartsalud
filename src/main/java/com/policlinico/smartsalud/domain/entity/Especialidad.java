package com.policlinico.smartsalud.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "especialidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100, unique = true)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "icono_url", length = 255)
    private String iconoUrl;

    @Column(nullable = false)
    private Boolean activa = true;
}
