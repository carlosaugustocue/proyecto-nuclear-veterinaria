package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.billing.Pago;
import com.veterinaria.domain.enums.MetodoPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de Pagos.
 * Implementa Repository Pattern.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    /**
     * Busca pagos por factura
     * @param facturaId ID de la factura
     * @return lista de pagos de la factura
     */
    @Query("SELECT p FROM Pago p WHERE p.factura.id = :facturaId " +
           "AND p.isActive = true ORDER BY p.fechaPago DESC")
    List<Pago> findByFacturaId(@Param("facturaId") Long facturaId);

    /**
     * Busca pagos por rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de pagos en el rango
     */
    @Query("SELECT p FROM Pago p WHERE p.fechaPago BETWEEN :fechaInicio AND :fechaFin " +
           "AND p.isActive = true ORDER BY p.fechaPago DESC")
    List<Pago> findByFechaPagoBetween(@Param("fechaInicio") LocalDate fechaInicio,
                                       @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca pagos por método de pago
     * @param metodoPago el método de pago
     * @return lista de pagos con ese método
     */
    @Query("SELECT p FROM Pago p WHERE p.metodoPago = :metodoPago " +
           "AND p.isActive = true ORDER BY p.fechaPago DESC")
    List<Pago> findByMetodoPago(@Param("metodoPago") MetodoPago metodoPago);

    /**
     * Busca pagos pendientes de verificación
     * @return lista de pagos no verificados
     */
    @Query("SELECT p FROM Pago p WHERE p.verificado = false " +
           "AND p.isActive = true ORDER BY p.fechaPago DESC")
    List<Pago> findPagosPendientesVerificacion();

    /**
     * Busca pagos por usuario que los registró
     * @param usuarioId ID del usuario
     * @return lista de pagos registrados por el usuario
     */
    @Query("SELECT p FROM Pago p WHERE p.usuarioRegistro.id = :usuarioId " +
           "AND p.isActive = true ORDER BY p.fechaPago DESC")
    List<Pago> findByUsuarioRegistroId(@Param("usuarioId") Long usuarioId);

    /**
     * Busca pago por número de referencia
     * @param numeroReferencia número de referencia
     * @return Optional con el pago
     */
    @Query("SELECT p FROM Pago p WHERE p.numeroReferencia = :numeroReferencia AND p.isActive = true")
    Optional<Pago> findByNumeroReferencia(@Param("numeroReferencia") String numeroReferencia);

    /**
     * Calcula el total de pagos por método en un rango de fechas
     * @param metodoPago método de pago
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return total de pagos
     */
    @Query("SELECT COALESCE(SUM(p.monto), 0.0) FROM Pago p " +
           "WHERE p.metodoPago = :metodoPago " +
           "AND p.fechaPago BETWEEN :fechaInicio AND :fechaFin " +
           "AND p.isActive = true")
    Double calcularTotalPorMetodoYFecha(@Param("metodoPago") MetodoPago metodoPago,
                                         @Param("fechaInicio") LocalDate fechaInicio,
                                         @Param("fechaFin") LocalDate fechaFin);

    /**
     * Calcula el total de comisiones por rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return total de comisiones
     */
    @Query("SELECT COALESCE(SUM(p.comision), 0.0) FROM Pago p " +
           "WHERE p.fechaPago BETWEEN :fechaInicio AND :fechaFin " +
           "AND p.isActive = true")
    Double calcularTotalComisionesByFecha(@Param("fechaInicio") LocalDate fechaInicio,
                                           @Param("fechaFin") LocalDate fechaFin);

    /**
     * Cuenta pagos por factura
     * @param facturaId ID de la factura
     * @return cantidad de pagos
     */
    @Query("SELECT COUNT(p) FROM Pago p WHERE p.factura.id = :facturaId AND p.isActive = true")
    Long countByFacturaId(@Param("facturaId") Long facturaId);

    /**
     * Busca un pago por ID solo si está activo
     * @param id ID del pago
     * @return Optional con el pago si está activo
     */
    @Query("SELECT p FROM Pago p WHERE p.id = :id AND p.isActive = true")
    Optional<Pago> findByIdAndActive(@Param("id") Long id);
}
