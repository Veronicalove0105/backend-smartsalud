package com.policlinico.smartsalud.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tarifa_consulta", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"medico_id", "sede_id", "vigente_desde"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TarifaConsulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false, length = 5)
    private String moneda = "PEN";

    @Column(name = "vigente_desde", nullable = false)
    private LocalDate vigenteDesde = LocalDate.now();

    @Column(name = "vigente_hasta")
    private LocalDate vigenteHasta;

    @Column(nullable = false)
    private Boolean activa = true;
}
