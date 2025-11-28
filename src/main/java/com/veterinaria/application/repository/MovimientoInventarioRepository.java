package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.inventory.MovimientoInventario;
import com.veterinaria.domain.entity.inventory.Producto;
import com.veterinaria.domain.enums.TipoMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repositorio para la gestión de Movimientos de Inventario.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Long> {

    /**
     * Busca movimientos por producto
     */
    @Query("SELECT m FROM MovimientoInventario m WHERE m.producto = :producto " +
           "AND m.isActive = true ORDER BY m.fechaMovimiento DESC")
    List<MovimientoInventario> findByProducto(@Param("producto") Producto producto);

    /**
     * Busca movimientos por tipo
     */
    @Query("SELECT m FROM MovimientoInventario m WHERE m.tipo = :tipo " +
           "AND m.isActive = true ORDER BY m.fechaMovimiento DESC")
    List<MovimientoInventario> findByTipo(@Param("tipo") TipoMovimiento tipo);

    /**
     * Busca movimientos en un rango de fechas
     */
    @Query("SELECT m FROM MovimientoInventario m WHERE m.fechaMovimiento BETWEEN :fechaInicio AND :fechaFin " +
           "AND m.isActive = true ORDER BY m.fechaMovimiento DESC")
    List<MovimientoInventario> findByFechaBetween(@Param("fechaInicio") LocalDateTime fechaInicio,
                                                    @Param("fechaFin") LocalDateTime fechaFin);

    /**
     * Busca movimientos por producto y tipo
     */
    @Query("SELECT m FROM MovimientoInventario m WHERE m.producto = :producto AND m.tipo = :tipo " +
           "AND m.isActive = true ORDER BY m.fechaMovimiento DESC")
    List<MovimientoInventario> findByProductoAndTipo(@Param("producto") Producto producto,
                                                       @Param("tipo") TipoMovimiento tipo);

    /**
     * Busca movimientos asociados a tratamientos
     */
    @Query("SELECT m FROM MovimientoInventario m WHERE m.tratamiento IS NOT NULL " +
           "AND m.isActive = true ORDER BY m.fechaMovimiento DESC")
    List<MovimientoInventario> findMovimientosDeTratamientos();

    /**
     * Busca movimientos por tratamiento específico
     */
    @Query("SELECT m FROM MovimientoInventario m WHERE m.tratamiento.id = :tratamientoId " +
           "AND m.isActive = true ORDER BY m.fechaMovimiento DESC")
    List<MovimientoInventario> findByTratamientoId(@Param("tratamientoId") Long tratamientoId);

    /**
     * Calcula total de entradas por producto
     */
    @Query("SELECT COALESCE(SUM(m.cantidad), 0) FROM MovimientoInventario m " +
           "WHERE m.producto = :producto AND m.tipo = 'ENTRADA' AND m.isActive = true")
    Long calcularTotalEntradas(@Param("producto") Producto producto);

    /**
     * Calcula total de salidas por producto
     */
    @Query("SELECT COALESCE(SUM(m.cantidad), 0) FROM MovimientoInventario m " +
           "WHERE m.producto = :producto AND m.tipo = 'SALIDA' AND m.isActive = true")
    Long calcularTotalSalidas(@Param("producto") Producto producto);

    /**
     * Busca últimos movimientos (límite configurable)
     */
    @Query("SELECT m FROM MovimientoInventario m WHERE m.isActive = true " +
           "ORDER BY m.fechaMovimiento DESC")
    List<MovimientoInventario> findUltimosMovimientos();

    /**
     * Busca movimientos por usuario
     */
    @Query("SELECT m FROM MovimientoInventario m WHERE m.usuario.id = :usuarioId " +
           "AND m.isActive = true ORDER BY m.fechaMovimiento DESC")
    List<MovimientoInventario> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}
