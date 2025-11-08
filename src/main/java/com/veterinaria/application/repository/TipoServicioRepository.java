package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.appointments.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la gestión de Tipos de Servicio.
 * Implementa Repository Pattern.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface TipoServicioRepository extends JpaRepository<TipoServicio, Long> {

    /**
     * Busca un tipo de servicio por nombre
     * @param nombre el nombre del servicio
     * @return Optional con el tipo de servicio si existe
     */
    @Query("SELECT ts FROM TipoServicio ts WHERE LOWER(ts.nombre) = LOWER(:nombre) AND ts.isActive = true")
    Optional<TipoServicio> findByNombre(@Param("nombre") String nombre);

    /**
     * Busca tipos de servicio por categoría
     * @param categoria la categoría
     * @return lista de tipos de servicio en esa categoría
     */
    @Query("SELECT ts FROM TipoServicio ts WHERE ts.categoria = :categoria AND ts.isActive = true " +
           "ORDER BY ts.nombre")
    List<TipoServicio> findByCategoria(@Param("categoria") String categoria);

    /**
     * Busca todos los tipos de servicio activos
     * @return lista de tipos de servicio activos
     */
    @Query("SELECT ts FROM TipoServicio ts WHERE ts.isActive = true ORDER BY ts.categoria, ts.nombre")
    List<TipoServicio> findAllActivos();

    /**
     * Busca tipos de servicio que requieren confirmación
     * @return lista de servicios que requieren confirmación
     */
    @Query("SELECT ts FROM TipoServicio ts WHERE ts.requiereConfirmacion = true AND ts.isActive = true " +
           "ORDER BY ts.nombre")
    List<TipoServicio> findServiciosQueRequierenConfirmacion();

    /**
     * Busca tipos de servicio por rango de precio
     * @param precioMin precio mínimo
     * @param precioMax precio máximo
     * @return lista de servicios en ese rango
     */
    @Query("SELECT ts FROM TipoServicio ts WHERE ts.precioBase BETWEEN :precioMin AND :precioMax " +
           "AND ts.isActive = true ORDER BY ts.precioBase")
    List<TipoServicio> findByPrecioBaseBetween(@Param("precioMin") Double precioMin,
                                                 @Param("precioMax") Double precioMax);

    /**
     * Busca tipos de servicio por duración estimada
     * @param duracionMin duración mínima en minutos
     * @param duracionMax duración máxima en minutos
     * @return lista de servicios en ese rango de duración
     */
    @Query("SELECT ts FROM TipoServicio ts WHERE ts.duracionEstimada BETWEEN :duracionMin AND :duracionMax " +
           "AND ts.isActive = true ORDER BY ts.duracionEstimada")
    List<TipoServicio> findByDuracionEstimadaBetween(@Param("duracionMin") Integer duracionMin,
                                                       @Param("duracionMax") Integer duracionMax);

    /**
     * Verifica si existe un tipo de servicio con ese nombre
     * @param nombre el nombre a verificar
     * @return true si existe
     */
    @Query("SELECT CASE WHEN COUNT(ts) > 0 THEN true ELSE false END FROM TipoServicio ts " +
           "WHERE LOWER(ts.nombre) = LOWER(:nombre) AND ts.isActive = true")
    boolean existsByNombre(@Param("nombre") String nombre);

    /**
     * Obtiene todas las categorías únicas de servicios
     * @return lista de categorías
     */
    @Query("SELECT DISTINCT ts.categoria FROM TipoServicio ts WHERE ts.isActive = true ORDER BY ts.categoria")
    List<String> findAllCategorias();

    /**
     * Cuenta servicios por categoría
     * @param categoria la categoría
     * @return cantidad de servicios
     */
    @Query("SELECT COUNT(ts) FROM TipoServicio ts WHERE ts.categoria = :categoria AND ts.isActive = true")
    Long countByCategoria(@Param("categoria") String categoria);
}
