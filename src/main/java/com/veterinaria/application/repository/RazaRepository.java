package com.veterinaria.application.repository;

import com.veterinaria.domain.entity.patients.Raza;
import com.veterinaria.domain.enums.TipoEspecie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para la entidad Raza.
 * Proporciona métodos de búsqueda y consulta para el catálogo de razas.
 *
 * @author Sistema Veterinaria
 */
@Repository
public interface RazaRepository extends JpaRepository<Raza, Long> {

    /**
     * Busca una raza por nombre y especie (combinación única)
     *
     * @param nombre nombre de la raza
     * @param especie especie
     * @return Optional con la raza si existe
     */
    Optional<Raza> findByNombreAndEspecie(String nombre, TipoEspecie especie);

    /**
     * Busca todas las razas de una especie específica
     *
     * @param especie especie
     * @return lista de razas
     */
    List<Raza> findByEspecie(TipoEspecie especie);

    /**
     * Busca todas las razas activas de una especie
     *
     * @param especie especie
     * @return lista de razas activas
     */
    @Query("SELECT r FROM Raza r WHERE r.especie = :especie AND r.isActive = true ORDER BY r.nombre")
    List<Raza> findActivasByEspecie(@Param("especie") TipoEspecie especie);

    /**
     * Busca todas las razas activas
     *
     * @return lista de razas activas
     */
    List<Raza> findByIsActiveTrueOrderByNombre();

    /**
     * Busca razas por nombre que contenga el texto (case insensitive)
     *
     * @param nombre texto a buscar
     * @return lista de razas que coinciden
     */
    List<Raza> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca todas las razas predefinidas
     *
     * @return lista de razas predefinidas
     */
    List<Raza> findByEsPredefinidaTrueOrderByEspecieAscNombreAsc();

    /**
     * Busca todas las razas personalizadas (no predefinidas)
     *
     * @return lista de razas personalizadas
     */
    List<Raza> findByEsPredefinidaFalseOrderByEspecieAscNombreAsc();

    /**
     * Busca todas las razas mestizas
     *
     * @return lista de razas mestizas
     */
    List<Raza> findByEsMestizoTrue();

    /**
     * Verifica si existe una raza con el nombre y especie dados
     *
     * @param nombre nombre de la raza
     * @param especie especie
     * @return true si existe
     */
    boolean existsByNombreAndEspecie(String nombre, TipoEspecie especie);

    /**
     * Cuenta razas por especie
     *
     * @param especie especie
     * @return cantidad de razas
     */
    long countByEspecie(TipoEspecie especie);

    /**
     * Cuenta razas activas por especie
     *
     * @param especie especie
     * @return cantidad de razas activas
     */
    @Query("SELECT COUNT(r) FROM Raza r WHERE r.especie = :especie AND r.isActive = true")
    long countActivasByEspecie(@Param("especie") TipoEspecie especie);

    /**
     * Busca razas de una especie que no sean mestizas y estén activas
     * Útil para obtener razas específicas sin incluir "Mestizo"
     *
     * @param especie especie
     * @return lista de razas específicas activas
     */
    @Query("SELECT r FROM Raza r WHERE r.especie = :especie " +
           "AND r.isActive = true " +
           "AND r.esMestizo = false " +
           "ORDER BY r.nombre")
    List<Raza> findRazasEspecificasActivasByEspecie(@Param("especie") TipoEspecie especie);
}
