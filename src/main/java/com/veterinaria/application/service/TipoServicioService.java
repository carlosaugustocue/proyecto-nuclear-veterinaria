package com.veterinaria.application.service;

import com.veterinaria.application.dto.tiposervicio.CreateTipoServicioRequest;
import com.veterinaria.application.dto.tiposervicio.TipoServicioDTO;
import com.veterinaria.application.dto.tiposervicio.UpdateTipoServicioRequest;

import java.util.List;

/**
 * Interfaz de servicio para la gestión de Tipos de Servicio.
 *
 * @author Sistema Veterinaria
 */
public interface TipoServicioService {

    /**
     * Crea un nuevo tipo de servicio
     * @param request datos del nuevo servicio
     * @return el DTO del servicio creado
     */
    TipoServicioDTO crear(CreateTipoServicioRequest request);

    /**
     * Actualiza un tipo de servicio existente
     * @param id ID del servicio
     * @param request datos a actualizar
     * @return el DTO del servicio actualizado
     */
    TipoServicioDTO actualizar(Long id, UpdateTipoServicioRequest request);

    /**
     * Busca un tipo de servicio por ID
     * @param id el ID
     * @return el DTO del servicio
     */
    TipoServicioDTO obtenerPorId(Long id);

    /**
     * Lista todos los tipos de servicio activos
     * @return lista de DTOs
     */
    List<TipoServicioDTO> listarTodos();

    /**
     * Lista tipos de servicio por categoría
     * @param categoria la categoría
     * @return lista de DTOs
     */
    List<TipoServicioDTO> listarPorCategoria(String categoria);

    /**
     * Obtiene todas las categorías de servicios
     * @return lista de categorías
     */
    List<String> obtenerCategorias();

    /**
     * Desactiva un tipo de servicio (soft delete)
     * @param id ID del servicio
     */
    void desactivar(Long id);

    /**
     * Activa un tipo de servicio
     * @param id ID del servicio
     */
    void activar(Long id);
}
