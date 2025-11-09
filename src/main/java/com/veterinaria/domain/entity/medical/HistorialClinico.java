package com.veterinaria.domain.entity.medical;

import com.veterinaria.domain.entity.BaseAuditableEntity;
import com.veterinaria.domain.entity.patients.Paciente;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa el historial clínico completo de un paciente.
 * Cada paciente tiene un único historial clínico que contiene todas sus consultas,
 * vacunas, exámenes y tratamientos.
 *
 * Relación: ONE-TO-ONE con Paciente
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "historiales_clinicos")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class HistorialClinico extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Paciente dueño del historial clínico
     * Relación bidireccional ONE-TO-ONE
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    /**
     * Fecha de creación del historial (primera consulta)
     */
    @Column(name = "fecha_apertura", nullable = false)
    private LocalDate fechaApertura;

    /**
     * Grupo sanguíneo del paciente (si se conoce)
     */
    @Column(name = "grupo_sanguineo", length = 10)
    private String grupoSanguineo;

    /**
     * Alergias conocidas del paciente
     */
    @Column(name = "alergias", columnDefinition = "TEXT")
    private String alergias;

    /**
     * Condiciones crónicas o enfermedades de base
     */
    @Column(name = "condiciones_cronicas", columnDefinition = "TEXT")
    private String condicionesCronicas;

    /**
     * Notas importantes del historial médico
     */
    @Column(name = "notas_importantes", columnDefinition = "TEXT")
    private String notasImportantes;

    /**
     * Consultas médicas del paciente
     * Relación ONE-TO-MANY con Consulta
     */
    @OneToMany(mappedBy = "historialClinico", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Consulta> consultas = new ArrayList<>();

    /**
     * Vacunas aplicadas al paciente
     * Relación ONE-TO-MANY con Vacuna
     */
    @OneToMany(mappedBy = "historialClinico", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Vacuna> vacunas = new ArrayList<>();

    /**
     * Exámenes realizados al paciente
     * Relación ONE-TO-MANY con Examen
     */
    @OneToMany(mappedBy = "historialClinico", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Examen> examenes = new ArrayList<>();

    // ==================== MÉTODOS DE NEGOCIO ====================

    /**
     * Agrega una consulta al historial
     * @param consulta la consulta a agregar
     */
    public void agregarConsulta(Consulta consulta) {
        consultas.add(consulta);
        consulta.setHistorialClinico(this);
    }

    /**
     * Agrega una vacuna al historial
     * @param vacuna la vacuna a agregar
     */
    public void agregarVacuna(Vacuna vacuna) {
        vacunas.add(vacuna);
        vacuna.setHistorialClinico(this);
    }

    /**
     * Agrega un examen al historial
     * @param examen el examen a agregar
     */
    public void agregarExamen(Examen examen) {
        examenes.add(examen);
        examen.setHistorialClinico(this);
    }

    /**
     * Obtiene la última consulta registrada
     * @return la última consulta o null si no hay consultas
     */
    public Consulta obtenerUltimaConsulta() {
        if (consultas.isEmpty()) {
            return null;
        }
        return consultas.get(consultas.size() - 1);
    }

    /**
     * Obtiene el total de consultas realizadas
     * @return número de consultas
     */
    public int getTotalConsultas() {
        return consultas.size();
    }

    /**
     * Obtiene el total de vacunas aplicadas
     * @return número de vacunas
     */
    public int getTotalVacunas() {
        return vacunas.size();
    }

    /**
     * Obtiene el total de exámenes realizados
     * @return número de exámenes
     */
    public int getTotalExamenes() {
        return examenes.size();
    }

    /**
     * Verifica si el paciente tiene alergias registradas
     * @return true si tiene alergias
     */
    public boolean tieneAlergias() {
        return alergias != null && !alergias.trim().isEmpty();
    }

    /**
     * Verifica si el paciente tiene condiciones crónicas
     * @return true si tiene condiciones crónicas
     */
    public boolean tieneCondicionesCronicas() {
        return condicionesCronicas != null && !condicionesCronicas.trim().isEmpty();
    }

    @Override
    public String toString() {
        return "HistorialClinico{" +
                "id=" + id +
                ", pacienteId=" + (paciente != null ? paciente.getId() : null) +
                ", fechaApertura=" + fechaApertura +
                ", totalConsultas=" + getTotalConsultas() +
                ", totalVacunas=" + getTotalVacunas() +
                '}';
    }
}
