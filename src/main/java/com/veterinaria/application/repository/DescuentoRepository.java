package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.billing.Descuento;
import com.veterinaria.domain.enums.TipoDescuento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de Descuentos.
 * Implementa Repository Pattern.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Long> {

    /**
     * Busca descuentos por factura
     * @param facturaId ID de la factura
     * @return lista de descuentos de la factura
     */
    @Query("SELECT d FROM Descuento d WHERE d.factura.id = :facturaId AND d.isActive = true")
    List<Descuento> findByFacturaId(@Param("facturaId") Long facturaId);

    /**
     * Busca un descuento por código
     * @param codigo código del descuento
     * @return Optional con el descuento
     */
    @Query("SELECT d FROM Descuento d WHERE d.codigo = :codigo " +
           "AND d.esValido = true AND d.isActive = true")
    Optional<Descuento> findByCodigo(@Param("codigo") String codigo);

    /**
     * Busca descuentos promocionales activos
     * @return lista de descuentos promocionales
     */
    @Query("SELECT d FROM Descuento d WHERE d.esPromocional = true " +
           "AND d.esValido = true AND d.isActive = true")
    List<Descuento> findDescuentosPromocionales();

    /**
     * Busca descuentos por tipo
     * @param tipo tipo de descuento
     * @return lista de descuentos de ese tipo
     */
    @Query("SELECT d FROM Descuento d WHERE d.tipo = :tipo AND d.isActive = true")
    List<Descuento> findByTipo(@Param("tipo") TipoDescuento tipo);

    /**
     * Verifica si existe un descuento con ese código
     * @param codigo código del descuento
     * @return true si existe
     */
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Descuento d " +
           "WHERE d.codigo = :codigo AND d.isActive = true")
    boolean existsByCodigo(@Param("codigo") String codigo);

    /**
     * Cuenta descuentos por factura
     * @param facturaId ID de la factura
     * @return cantidad de descuentos
     */
    @Query("SELECT COUNT(d) FROM Descuento d WHERE d.factura.id = :facturaId AND d.isActive = true")
    Long countByFacturaId(@Param("facturaId") Long facturaId);
}
