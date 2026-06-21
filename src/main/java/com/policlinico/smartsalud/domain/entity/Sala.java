package com.policlinico.smartsalud.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "sala", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"sede_id", "nombre"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;

    @Column(nullable = false, length = 100)
    private String nombre;

    private Short piso;

    @Column(length = 20)
    private String numero;

    @Column(length = 50)
    private String tipo;

    @Column(nullable = false)
    private Boolean activa = true;
}
