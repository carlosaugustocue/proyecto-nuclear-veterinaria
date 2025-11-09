package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.billing.DetalleFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la gestión de Detalles de Factura.
 * Implementa Repository Pattern.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface DetalleFacturaRepository extends JpaRepository<DetalleFactura, Long> {

    /**
     * Busca detalles por factura
     * @param facturaId ID de la factura
     * @return lista de detalles de la factura
     */
    @Query("SELECT d FROM DetalleFactura d WHERE d.factura.id = :facturaId AND d.isActive = true")
    List<DetalleFactura> findByFacturaId(@Param("facturaId") Long facturaId);

    /**
     * Busca detalles por tipo de servicio
     * @param tipoServicioId ID del tipo de servicio
     * @return lista de detalles con ese servicio
     */
    @Query("SELECT d FROM DetalleFactura d WHERE d.tipoServicio.id = :tipoServicioId AND d.isActive = true")
    List<DetalleFactura> findByTipoServicioId(@Param("tipoServicioId") Long tipoServicioId);

    /**
     * Cuenta detalles por factura
     * @param facturaId ID de la factura
     * @return cantidad de detalles
     */
    @Query("SELECT COUNT(d) FROM DetalleFactura d WHERE d.factura.id = :facturaId AND d.isActive = true")
    Long countByFacturaId(@Param("facturaId") Long facturaId);
}
