package com.policlinico.smartsalud.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "medico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 15, unique = true)
    private String dni;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, length = 20, unique = true)
    private String cmp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "especialidad_id", nullable = false)
    private Especialidad especialidad;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(name = "foto_url", length = 255)
    private String fotoUrl;

    @Column(name = "anios_experiencia")
    private Short aniosExperiencia = 0;

    @Column(name = "descripcion_profesional", columnDefinition = "TEXT")
    private String descripcionProfesional;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso = LocalDate.now();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "medico_sede",
        joinColumns = @JoinColumn(name = "medico_id"),
        inverseJoinColumns = @JoinColumn(name = "sede_id")
    )
    private Set<Sede> sedes;
}
