package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.appointments.CreateTipoServicioRequest;
import com.veterinaria.application.dto.appointments.TipoServicioDTO;
import com.veterinaria.application.dto.appointments.UpdateTipoServicioRequest;
import com.veterinaria.application.exception.DuplicateResourceException;
import com.veterinaria.application.exception.ResourceNotFoundException;
import com.veterinaria.application.mapper.TipoServicioMapper;
import com.veterinaria.application.repository.TipoServicioRepository;
import com.veterinaria.application.service.TipoServicioService;
import com.veterinaria.domain.entity.appointments.TipoServicio;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementación del servicio de Tipos de Servicio.
 * Contiene la lógica de negocio para gestión de servicios veterinarios.
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TipoServicioServiceImpl implements TipoServicioService {

    private final TipoServicioRepository tipoServicioRepository;
    private final TipoServicioMapper tipoServicioMapper;

    @Override
    @Transactional
    public TipoServicioDTO crear(CreateTipoServicioRequest request) {
        log.info("Creando nuevo tipo de servicio: {}", request.getNombre());

        // Validar que no exista un servicio con el mismo nombre
        if (tipoServicioRepository.existsByNombre(request.getNombre())) {
            throw new DuplicateResourceException(
                    "Ya existe un tipo de servicio con el nombre: " + request.getNombre()
            );
        }

        // Crear entidad usando mapper
        TipoServicio tipoServicio = tipoServicioMapper.toEntity(request);

        // Guardar en base de datos
        TipoServicio guardado = tipoServicioRepository.save(tipoServicio);

        log.info("Tipo de servicio creado exitosamente con ID: {}", guardado.getId());
        return tipoServicioMapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public TipoServicioDTO actualizar(Long id, UpdateTipoServicioRequest request) {
        log.info("Actualizando tipo de servicio ID: {}", id);

        TipoServicio tipoServicio = tipoServicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de servicio no encontrado con ID: " + id
                ));

        // Validar nombre único si se está cambiando
        if (request.getNombre() != null && !request.getNombre().equals(tipoServicio.getNombre())) {
            if (tipoServicioRepository.existsByNombre(request.getNombre())) {
                throw new DuplicateResourceException(
                        "Ya existe un tipo de servicio con el nombre: " + request.getNombre()
                );
            }
        }

        // Actualizar campos usando mapper
        tipoServicioMapper.updateEntityFromDTO(request, tipoServicio);

        // Guardar cambios
        TipoServicio actualizado = tipoServicioRepository.save(tipoServicio);

        log.info("Tipo de servicio actualizado exitosamente ID: {}", id);
        return tipoServicioMapper.toDTO(actualizado);
    }

    @Override
    public TipoServicioDTO obtenerPorId(Long id) {
        log.debug("Buscando tipo de servicio con ID: {}", id);

        TipoServicio tipoServicio = tipoServicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de servicio no encontrado con ID: " + id
                ));

        return tipoServicioMapper.toDTO(tipoServicio);
    }

    @Override
    public List<TipoServicioDTO> listarTodos() {
        log.debug("Listando todos los tipos de servicio activos");

        List<TipoServicio> servicios = tipoServicioRepository.findAllActivos();
        return tipoServicioMapper.toDTOList(servicios);
    }

    @Override
    public List<TipoServicioDTO> listarPorCategoria(String categoria) {
        log.debug("Listando tipos de servicio por categoría: {}", categoria);

        List<TipoServicio> servicios = tipoServicioRepository.findByCategoria(categoria);
        return tipoServicioMapper.toDTOList(servicios);
    }

    @Override
    public List<String> obtenerCategorias() {
        log.debug("Obteniendo todas las categorías de servicios");

        return tipoServicioRepository.findAllCategorias();
    }

    @Override
    @Transactional
    public void desactivar(Long id) {
        log.info("Desactivando tipo de servicio ID: {}", id);

        TipoServicio tipoServicio = tipoServicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de servicio no encontrado con ID: " + id
                ));

        tipoServicio.setIsActive(false);
        tipoServicioRepository.save(tipoServicio);

        log.info("Tipo de servicio desactivado exitosamente ID: {}", id);
    }

    @Override
    @Transactional
    public void activar(Long id) {
        log.info("Activando tipo de servicio ID: {}", id);

        TipoServicio tipoServicio = tipoServicioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Tipo de servicio no encontrado con ID: " + id
                ));

        tipoServicio.setIsActive(true);
        tipoServicioRepository.save(tipoServicio);

        log.info("Tipo de servicio activado exitosamente ID: {}", id);
    }
}
