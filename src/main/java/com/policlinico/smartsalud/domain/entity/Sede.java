package com.policlinico.smartsalud.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sede")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sede {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150, unique = true)
    private String nombre;

    @Column(nullable = false, length = 255)
    private String direccion;

    @Column(length = 100)
    private String distrito;

    @Column(nullable = false, length = 100)
    private String ciudad = "Lima";

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(nullable = false)
    private Boolean activa = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}
