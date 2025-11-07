package com.veterinaria.domain.entity.security;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entidad que representa un dispositivo confiable de un usuario.
 * Utilizado para detección de nuevos dispositivos y seguridad.
 *
 * @author Sistema Veterinaria
 */
@Entity
@Table(name = "dispositivos_confiables", indexes = {
    @Index(name = "idx_usuario", columnList = "usuario_id"),
    @Index(name = "idx_huella", columnList = "huella")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispositivoConfiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Usuario propietario del dispositivo
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * Huella digital del dispositivo (generada en el cliente)
     */
    @Column(nullable = false, length = 255)
    private String huella;

    /**
     * Nombre descriptivo del dispositivo
     */
    @Column(length = 100)
    private String nombreDispositivo;

    /**
     * Fecha de registro del dispositivo
     */
    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    /**
     * Verifica si la huella coincide
     *
     * @param otraHuella huella a comparar
     * @return true si coinciden
     */
    public boolean esConfiable(String otraHuella) {
        return this.huella != null && this.huella.equals(otraHuella);
    }

    @Override
    public String toString() {
        return "DispositivoConfiable{" +
                "id=" + id +
                ", nombreDispositivo='" + nombreDispositivo + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
