package com.policlinico.smartsalud.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pago", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"cita_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cita_id", nullable = false)
    private Cita cita;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false, length = 5)
    private String moneda = "PEN";

    @Column(nullable = false, length = 50)
    private String metodo;

    @Column(nullable = false, length = 20)
    private String estado = "PENDIENTE";

    @Column(name = "referencia_externa", length = 100)
    private String referenciaExterna;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(name = "fecha_confirmacion")
    private LocalDateTime fechaConfirmacion;

    @Column(nullable = false)
    private Short intentos = 0;

    @Column(columnDefinition = "TEXT")
    private String notas;
}
