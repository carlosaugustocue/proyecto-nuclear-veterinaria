package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.inventory.Producto;
import com.veterinaria.domain.enums.CategoriaProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de Productos.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    /**
     * Busca un producto por código
     */
    @Query("SELECT p FROM Producto p WHERE p.codigo = :codigo AND p.isActive = true")
    Optional<Producto> findByCodigo(@Param("codigo") String codigo);

    /**
     * Busca productos por categoría
     */
    @Query("SELECT p FROM Producto p WHERE p.categoria = :categoria AND p.isActive = true ORDER BY p.nombre")
    List<Producto> findByCategoria(@Param("categoria") CategoriaProducto categoria);

    /**
     * Busca productos con stock bajo (stock actual <= stock mínimo)
     */
    @Query("SELECT p FROM Producto p WHERE p.stockActual <= p.stockMinimo AND p.isActive = true ORDER BY p.stockActual")
    List<Producto> findProductosConStockBajo();

    /**
     * Busca productos por vencer (próximos 30 días)
     */
    @Query("SELECT p FROM Producto p WHERE p.fechaVencimiento IS NOT NULL " +
           "AND p.fechaVencimiento BETWEEN :fechaActual AND :fechaLimite " +
           "AND p.isActive = true ORDER BY p.fechaVencimiento")
    List<Producto> findProductosPorVencer(@Param("fechaActual") LocalDate fechaActual,
                                          @Param("fechaLimite") LocalDate fechaLimite);

    /**
     * Busca productos vencidos
     */
    @Query("SELECT p FROM Producto p WHERE p.fechaVencimiento IS NOT NULL " +
           "AND p.fechaVencimiento < :fechaActual AND p.isActive = true")
    List<Producto> findProductosVencidos(@Param("fechaActual") LocalDate fechaActual);

    /**
     * Busca productos por nombre (búsqueda parcial)
     */
    @Query("SELECT p FROM Producto p WHERE LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) " +
           "AND p.isActive = true ORDER BY p.nombre")
    List<Producto> findByNombreContaining(@Param("nombre") String nombre);

    /**
     * Busca productos por proveedor
     */
    @Query("SELECT p FROM Producto p WHERE LOWER(p.proveedor) = LOWER(:proveedor) " +
           "AND p.isActive = true ORDER BY p.nombre")
    List<Producto> findByProveedor(@Param("proveedor") String proveedor);

    /**
     * Verifica si existe un producto con ese código
     */
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Producto p " +
           "WHERE p.codigo = :codigo AND p.isActive = true")
    boolean existsByCodigo(@Param("codigo") String codigo);

    /**
     * Cuenta productos por categoría
     */
    @Query("SELECT COUNT(p) FROM Producto p WHERE p.categoria = :categoria AND p.isActive = true")
    Long countByCategoria(@Param("categoria") CategoriaProducto categoria);

    /**
     * Calcula el valor total del inventario
     */
    @Query("SELECT SUM(p.stockActual * p.precioCompra) FROM Producto p WHERE p.isActive = true")
    Double calcularValorTotalInventario();

    /**
     * Busca todos los productos activos
     */
    @Query("SELECT p FROM Producto p WHERE p.isActive = true ORDER BY p.nombre")
    List<Producto> findAllActive();
}
