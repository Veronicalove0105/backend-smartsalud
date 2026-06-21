package com.policlinico.smartsalud.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario_rol", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"entidad", "entidad_id", "rol_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioRol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 20)
    private String entidad; // PACIENTE | MEDICO | STAFF

    @Column(name = "entidad_id", nullable = false)
    private Integer entidadId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @Column(name = "asignado_en", nullable = false, updatable = false)
    private LocalDateTime asignadoEn = LocalDateTime.now();
}
