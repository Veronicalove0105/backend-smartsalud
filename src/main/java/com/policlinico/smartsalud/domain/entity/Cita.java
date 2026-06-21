package com.policlinico.smartsalud.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "cita", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"medico_id", "fecha", "hora"}),
    @UniqueConstraint(columnNames = {"codigo_reserva"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sede_id", nullable = false)
    private Sede sede;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id")
    private Sala sala;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "horario_id")
    private HorarioMedico horario;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(nullable = false)
    private LocalTime hora;

    @Column(name = "duracion_min", nullable = false)
    private Short duracionMin = 30;

    @Column(name = "tipo_consulta", nullable = false, length = 50)
    private String tipoConsulta = "PRIMERA_VEZ";

    @Column(nullable = false, length = 20)
    private String modalidad = "PRESENCIAL";

    @Column(nullable = false, length = 20)
    private String estado = "RESERVADO";

    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;

    @Column(name = "notas_admin", columnDefinition = "TEXT")
    private String notasAdmin;

    @Column(name = "codigo_reserva", nullable = false, length = 20)
    private String codigoReserva;

    @Column(nullable = false, length = 20)
    private String origen = "WEB";

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Column(name = "fecha_cancelacion")
    private LocalDateTime fechaCancelacion;

    @Column(name = "motivo_cancelacion", length = 255)
    private String motivoCancelacion;

    @Column(name = "cancelado_por", length = 50)
    private String canceladoPor;
}
