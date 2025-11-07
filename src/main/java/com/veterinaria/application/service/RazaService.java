package com.veterinaria.application.service;

import com.veterinaria.application.dto.raza.CreateRazaRequest;
import com.veterinaria.application.dto.raza.RazaDTO;
import com.veterinaria.application.dto.raza.UpdateRazaRequest;
import com.veterinaria.domain.enums.TipoEspecie;

import java.util.List;
import java.util.Map;

/**
 * Interfaz del servicio para gestión de Razas.
 * Define las operaciones CRUD y consultas específicas.
 *
 * @author Sistema Veterinaria
 */
public interface RazaService {

    /**
     * Crea una nueva raza
     *
     * @param request datos de la raza
     * @return RazaDTO creada
     */
    RazaDTO crearRaza(CreateRazaRequest request);

    /**
     * Obtiene una raza por su ID
     *
     * @param id ID de la raza
     * @return RazaDTO
     */
    RazaDTO obtenerRazaPorId(Long id);

    /**
     * Obtiene todas las razas
     *
     * @return lista de razas
     */
    List<RazaDTO> obtenerTodasLasRazas();

    /**
     * Obtiene todas las razas activas
     *
     * @return lista de razas activas
     */
    List<RazaDTO> obtenerRazasActivas();

    /**
     * Obtiene todas las razas de una especie específica
     *
     * @param especie especie
     * @return lista de razas de esa especie
     */
    List<RazaDTO> obtenerRazasPorEspecie(TipoEspecie especie);

    /**
     * Obtiene todas las razas activas de una especie
     *
     * @param especie especie
     * @return lista de razas activas de esa especie
     */
    List<RazaDTO> obtenerRazasActivasPorEspecie(TipoEspecie especie);

    /**
     * Obtiene razas específicas (no mestizas) activas por especie
     *
     * @param especie especie
     * @return lista de razas específicas activas
     */
    List<RazaDTO> obtenerRazasEspecificasActivasPorEspecie(TipoEspecie especie);

    /**
     * Busca razas por nombre (búsqueda parcial)
     *
     * @param nombre nombre a buscar
     * @return lista de razas que coinciden
     */
    List<RazaDTO> buscarPorNombre(String nombre);

    /**
     * Obtiene todas las razas predefinidas
     *
     * @return lista de razas predefinidas
     */
    List<RazaDTO> obtenerRazasPredefinidas();

    /**
     * Obtiene todas las razas personalizadas (no predefinidas)
     *
     * @return lista de razas personalizadas
     */
    List<RazaDTO> obtenerRazasPersonalizadas();

    /**
     * Actualiza una raza existente
     *
     * @param id ID de la raza
     * @param request datos a actualizar
     * @return RazaDTO actualizada
     */
    RazaDTO actualizarRaza(Long id, UpdateRazaRequest request);

    /**
     * Activa una raza
     *
     * @param id ID de la raza
     * @return RazaDTO activada
     */
    RazaDTO activarRaza(Long id);

    /**
     * Desactiva una raza (no la elimina)
     *
     * @param id ID de la raza
     * @return RazaDTO desactivada
     */
    RazaDTO desactivarRaza(Long id);

    /**
     * Elimina una raza (eliminación física)
     * Solo si no tiene pacientes asociados
     *
     * @param id ID de la raza
     */
    void eliminarRaza(Long id);

    /**
     * Cuenta razas por especie
     *
     * @param especie especie
     * @return cantidad de razas
     */
    long contarRazasPorEspecie(TipoEspecie especie);

    /**
     * Obtiene estadísticas de razas por especie
     *
     * @return mapa con estadísticas (especie -> cantidad)
     */
    Map<TipoEspecie, Long> obtenerEstadisticasPorEspecie();

    /**
     * Verifica si existe una raza con ese nombre y especie
     *
     * @param nombre nombre de la raza
     * @param especie especie
     * @return true si existe
     */
    boolean existeRaza(String nombre, TipoEspecie especie);
}
