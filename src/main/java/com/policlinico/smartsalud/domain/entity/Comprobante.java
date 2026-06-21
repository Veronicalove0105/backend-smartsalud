package com.policlinico.smartsalud.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "comprobante", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"pago_id"}),
    @UniqueConstraint(columnNames = {"serie", "numero"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pago_id", nullable = false)
    private Pago pago;

    @Column(nullable = false, length = 20)
    private String tipo = "BOLETA";

    @Column(nullable = false, length = 10)
    private String serie;

    @Column(nullable = false, length = 10)
    private String numero;

    @Column(name = "ruc_emisor", length = 11)
    private String rucEmisor;

    @Column(name = "razon_social", length = 200)
    private String razonSocial;

    @Column(name = "ruc_receptor", length = 11)
    private String rucReceptor;

    @Column(name = "razon_social_receptor", length = 200)
    private String razonSocialReceptor;

    @Column(precision = 10, scale = 2)
    private BigDecimal subtotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal igv;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "ruta_pdf", columnDefinition = "TEXT")
    private String rutaPdf;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision = LocalDateTime.now();
}
