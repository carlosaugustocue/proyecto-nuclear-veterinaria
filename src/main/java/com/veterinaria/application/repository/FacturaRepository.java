package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.billing.Factura;
import com.veterinaria.domain.entity.patients.Cliente;
import com.veterinaria.domain.enums.EstadoFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de Facturas.
 * Implementa Repository Pattern.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    /**
     * Busca una factura por su número
     * @param numeroFactura número único de la factura
     * @return Optional con la factura si existe
     */
    @Query("SELECT f FROM Factura f WHERE f.numeroFactura = :numeroFactura AND f.isActive = true")
    Optional<Factura> findByNumeroFactura(@Param("numeroFactura") String numeroFactura);

    /**
     * Busca facturas por cliente
     * @param clienteId ID del cliente
     * @return lista de facturas del cliente
     */
    @Query("SELECT f FROM Factura f WHERE f.cliente.id = :clienteId " +
           "AND f.isActive = true ORDER BY f.fechaEmision DESC")
    List<Factura> findByClienteId(@Param("clienteId") Long clienteId);

    /**
     * Busca facturas por cliente (entidad)
     * @param cliente el cliente
     * @return lista de facturas del cliente
     */
    @Query("SELECT f FROM Factura f WHERE f.cliente = :cliente " +
           "AND f.isActive = true ORDER BY f.fechaEmision DESC")
    List<Factura> findByCliente(@Param("cliente") Cliente cliente);

    /**
     * Busca facturas por estado
     * @param estado el estado de la factura
     * @return lista de facturas con ese estado
     */
    @Query("SELECT f FROM Factura f WHERE f.estado = :estado " +
           "AND f.isActive = true ORDER BY f.fechaEmision DESC")
    List<Factura> findByEstado(@Param("estado") EstadoFactura estado);

    /**
     * Busca facturas por rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return lista de facturas en el rango
     */
    @Query("SELECT f FROM Factura f WHERE f.fechaEmision BETWEEN :fechaInicio AND :fechaFin " +
           "AND f.isActive = true ORDER BY f.fechaEmision DESC")
    List<Factura> findByFechaEmisionBetween(@Param("fechaInicio") LocalDate fechaInicio,
                                             @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca facturas vencidas (con fecha de vencimiento pasada y no pagadas)
     * @param fechaActual fecha actual
     * @return lista de facturas vencidas
     */
    @Query("SELECT f FROM Factura f WHERE f.fechaVencimiento < :fechaActual " +
           "AND f.estado IN ('PENDIENTE', 'PARCIAL') " +
           "AND f.isActive = true ORDER BY f.fechaVencimiento")
    List<Factura> findFacturasVencidas(@Param("fechaActual") LocalDate fechaActual);

    /**
     * Busca facturas próximas a vencer (en los próximos N días)
     * @param fechaInicio fecha inicial (hoy)
     * @param fechaFin fecha límite
     * @return lista de facturas
     */
    @Query("SELECT f FROM Factura f WHERE f.fechaVencimiento BETWEEN :fechaInicio AND :fechaFin " +
           "AND f.estado IN ('PENDIENTE', 'PARCIAL') " +
           "AND f.isActive = true ORDER BY f.fechaVencimiento")
    List<Factura> findFacturasProximasAVencer(@Param("fechaInicio") LocalDate fechaInicio,
                                               @Param("fechaFin") LocalDate fechaFin);

    /**
     * Busca facturas pendientes de pago
     * @return lista de facturas pendientes
     */
    @Query("SELECT f FROM Factura f WHERE f.estado = 'PENDIENTE' " +
           "AND f.isActive = true ORDER BY f.fechaEmision DESC")
    List<Factura> findFacturasPendientes();

    /**
     * Busca facturas con saldo pendiente
     * @return lista de facturas con saldo pendiente
     */
    @Query("SELECT f FROM Factura f WHERE f.saldoPendiente > 0 " +
           "AND f.estado IN ('PENDIENTE', 'PARCIAL') " +
           "AND f.isActive = true ORDER BY f.fechaEmision DESC")
    List<Factura> findFacturasConSaldoPendiente();

    /**
     * Busca facturas por cita
     * @param citaId ID de la cita
     * @return Optional con la factura asociada a la cita
     */
    @Query("SELECT f FROM Factura f WHERE f.cita.id = :citaId AND f.isActive = true")
    Optional<Factura> findByCitaId(@Param("citaId") Long citaId);

    /**
     * Busca facturas por usuario emisor
     * @param usuarioId ID del usuario emisor
     * @return lista de facturas emitidas por el usuario
     */
    @Query("SELECT f FROM Factura f WHERE f.usuarioEmisor.id = :usuarioId " +
           "AND f.isActive = true ORDER BY f.fechaEmision DESC")
    List<Factura> findByUsuarioEmisorId(@Param("usuarioId") Long usuarioId);

    /**
     * Calcula el total de ventas por rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return total de ventas
     */
    @Query("SELECT COALESCE(SUM(f.total), 0.0) FROM Factura f " +
           "WHERE f.fechaEmision BETWEEN :fechaInicio AND :fechaFin " +
           "AND f.estado != 'ANULADA' AND f.isActive = true")
    Double calcularTotalVentasByFecha(@Param("fechaInicio") LocalDate fechaInicio,
                                       @Param("fechaFin") LocalDate fechaFin);

    /**
     * Calcula el total pagado por rango de fechas
     * @param fechaInicio fecha inicial
     * @param fechaFin fecha final
     * @return total pagado
     */
    @Query("SELECT COALESCE(SUM(f.totalPagado), 0.0) FROM Factura f " +
           "WHERE f.fechaEmision BETWEEN :fechaInicio AND :fechaFin " +
           "AND f.estado != 'ANULADA' AND f.isActive = true")
    Double calcularTotalPagadoByFecha(@Param("fechaInicio") LocalDate fechaInicio,
                                       @Param("fechaFin") LocalDate fechaFin);

    /**
     * Calcula el saldo pendiente total
     * @return saldo pendiente total
     */
    @Query("SELECT COALESCE(SUM(f.saldoPendiente), 0.0) FROM Factura f " +
           "WHERE f.estado IN ('PENDIENTE', 'PARCIAL') AND f.isActive = true")
    Double calcularSaldoPendienteTotal();

    /**
     * Cuenta facturas por estado
     * @param estado el estado
     * @return cantidad de facturas
     */
    @Query("SELECT COUNT(f) FROM Factura f WHERE f.estado = :estado AND f.isActive = true")
    Long countByEstado(@Param("estado") EstadoFactura estado);

    /**
     * Cuenta facturas por cliente
     * @param clienteId ID del cliente
     * @return cantidad de facturas del cliente
     */
    @Query("SELECT COUNT(f) FROM Factura f WHERE f.cliente.id = :clienteId AND f.isActive = true")
    Long countByClienteId(@Param("clienteId") Long clienteId);

    /**
     * Verifica si existe una factura con ese número
     * @param numeroFactura número de factura
     * @return true si existe
     */
    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END FROM Factura f " +
           "WHERE f.numeroFactura = :numeroFactura AND f.isActive = true")
    boolean existsByNumeroFactura(@Param("numeroFactura") String numeroFactura);

    /**
     * Busca una factura por ID solo si está activa
     * @param id ID de la factura
     * @return Optional con la factura si está activa
     */
    @Query("SELECT f FROM Factura f WHERE f.id = :id AND f.isActive = true")
    Optional<Factura> findByIdAndActive(@Param("id") Long id);

    /**
     * Obtiene el último número de factura del año actual
     * @param year año actual
     * @return el último número generado
     */
    @Query("SELECT f.numeroFactura FROM Factura f " +
           "WHERE FUNCTION('YEAR', f.fechaEmision) = :year " +
           "AND f.isActive = true ORDER BY f.id DESC LIMIT 1")
    Optional<String> findUltimoNumeroFacturaDelAnio(@Param("year") int year);
}
