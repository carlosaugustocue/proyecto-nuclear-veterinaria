package com.veterinaria.application.service.impl;

import com.veterinaria.application.dto.raza.CreateRazaRequest;
import com.veterinaria.application.dto.raza.RazaDTO;
import com.veterinaria.application.dto.raza.UpdateRazaRequest;
import com.veterinaria.application.mapper.RazaMapper;
import com.veterinaria.application.repository.RazaRepository;
import com.veterinaria.application.service.RazaService;
import com.veterinaria.domain.entity.patients.Raza;
import com.veterinaria.domain.enums.TipoEspecie;
import com.veterinaria.infrastructure.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementación del servicio de gestión de Razas.
 * Implementa HU-09: Tipos de Mascotas
 *
 * @author Sistema Veterinaria
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RazaServiceImpl implements RazaService {

    private final RazaRepository razaRepository;
    private final RazaMapper razaMapper;

    private static final String RAZA_NO_ENCONTRADA = "Raza no encontrada con ID: %d";
    private static final String RAZA_YA_EXISTE = "Ya existe una raza '%s' para la especie %s";

    @Override
    @Transactional
    public RazaDTO crearRaza(CreateRazaRequest request) {
        log.info("Creando nueva raza: {} para especie: {}", request.nombre(), request.especie());

        // Validar que no exista la combinación nombre-especie
        if (razaRepository.existsByNombreAndEspecie(request.nombre(), request.especie())) {
            log.warn("Intento de crear raza duplicada: {} - {}", request.nombre(), request.especie());
            throw new IllegalArgumentException(
                String.format(RAZA_YA_EXISTE, request.nombre(), request.especie())
            );
        }

        Raza raza = Raza.builder()
                .nombre(request.nombre())
                .especie(request.especie())
                .descripcion(request.descripcion())
                .esPredefinida(false) // Las creadas por usuario son personalizadas
                .esMestizo(request.esMestizo() != null ? request.esMestizo() : false)
                .tamanioTipico(request.tamanioTipico())
                .pesoPromedioKg(request.pesoPromedioKg())
                .isActive(true)
                .build();

        Raza razaGuardada = razaRepository.save(raza);
        log.info("Raza creada exitosamente con ID: {}", razaGuardada.getId());

        return razaMapper.toDTO(razaGuardada);
    }

    @Override
    public RazaDTO obtenerRazaPorId(Long id) {
        log.debug("Buscando raza con ID: {}", id);

        Raza raza = razaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Raza no encontrada con ID: {}", id);
                    return new ResourceNotFoundException(String.format(RAZA_NO_ENCONTRADA, id));
                });

        return razaMapper.toDTO(raza);
    }

    @Override
    public List<RazaDTO> obtenerTodasLasRazas() {
        log.debug("Obteniendo todas las razas");

        List<Raza> razas = razaRepository.findAll();
        log.info("Se encontraron {} razas", razas.size());

        return razas.stream()
                .map(razaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RazaDTO> obtenerRazasActivas() {
        log.debug("Obteniendo razas activas");

        List<Raza> razas = razaRepository.findByIsActiveTrueOrderByNombre();
        log.info("Se encontraron {} razas activas", razas.size());

        return razas.stream()
                .map(razaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RazaDTO> obtenerRazasPorEspecie(TipoEspecie especie) {
        log.debug("Obteniendo razas de especie: {}", especie);

        List<Raza> razas = razaRepository.findByEspecie(especie);
        log.info("Se encontraron {} razas de especie {}", razas.size(), especie);

        return razas.stream()
                .map(razaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RazaDTO> obtenerRazasActivasPorEspecie(TipoEspecie especie) {
        log.debug("Obteniendo razas activas de especie: {}", especie);

        List<Raza> razas = razaRepository.findActivasByEspecie(especie);
        log.info("Se encontraron {} razas activas de especie {}", razas.size(), especie);

        return razas.stream()
                .map(razaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RazaDTO> obtenerRazasEspecificasActivasPorEspecie(TipoEspecie especie) {
        log.debug("Obteniendo razas específicas activas de especie: {}", especie);

        List<Raza> razas = razaRepository.findRazasEspecificasActivasByEspecie(especie);
        log.info("Se encontraron {} razas específicas activas de especie {}", razas.size(), especie);

        return razas.stream()
                .map(razaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RazaDTO> buscarPorNombre(String nombre) {
        log.debug("Buscando razas por nombre: {}", nombre);

        List<Raza> razas = razaRepository.findByNombreContainingIgnoreCase(nombre);
        log.info("Se encontraron {} razas con el nombre: {}", razas.size(), nombre);

        return razas.stream()
                .map(razaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RazaDTO> obtenerRazasPredefinidas() {
        log.debug("Obteniendo razas predefinidas");

        List<Raza> razas = razaRepository.findByEsPredefinidaTrueOrderByEspecieAscNombreAsc();
        log.info("Se encontraron {} razas predefinidas", razas.size());

        return razas.stream()
                .map(razaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RazaDTO> obtenerRazasPersonalizadas() {
        log.debug("Obteniendo razas personalizadas");

        List<Raza> razas = razaRepository.findByEsPredefinidaFalseOrderByEspecieAscNombreAsc();
        log.info("Se encontraron {} razas personalizadas", razas.size());

        return razas.stream()
                .map(razaMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RazaDTO actualizarRaza(Long id, UpdateRazaRequest request) {
        log.info("Actualizando raza con ID: {}", id);

        Raza raza = razaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Raza no encontrada con ID: {}", id);
                    return new ResourceNotFoundException(String.format(RAZA_NO_ENCONTRADA, id));
                });

        // Actualizar campos opcionales
        if (request.nombre() != null && !request.nombre().isBlank()) {
            // Validar que no exista otra raza con ese nombre y especie
            if (!raza.getNombre().equals(request.nombre()) &&
                razaRepository.existsByNombreAndEspecie(request.nombre(), raza.getEspecie())) {
                throw new IllegalArgumentException(
                    String.format(RAZA_YA_EXISTE, request.nombre(), raza.getEspecie())
                );
            }
            raza.setNombre(request.nombre());
        }

        if (request.descripcion() != null) {
            raza.setDescripcion(request.descripcion());
        }

        if (request.esMestizo() != null) {
            raza.setEsMestizo(request.esMestizo());
        }

        if (request.tamanioTipico() != null) {
            raza.setTamanioTipico(request.tamanioTipico());
        }

        if (request.pesoPromedioKg() != null) {
            raza.setPesoPromedioKg(request.pesoPromedioKg());
        }

        if (request.isActive() != null) {
            raza.setIsActive(request.isActive());
        }

        Raza razaActualizada = razaRepository.save(raza);
        log.info("Raza actualizada exitosamente con ID: {}", razaActualizada.getId());

        return razaMapper.toDTO(razaActualizada);
    }

    @Override
    @Transactional
    public RazaDTO activarRaza(Long id) {
        log.info("Activando raza con ID: {}", id);

        Raza raza = razaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Raza no encontrada con ID: {}", id);
                    return new ResourceNotFoundException(String.format(RAZA_NO_ENCONTRADA, id));
                });

        raza.activar();
        Raza razaActualizada = razaRepository.save(raza);

        log.info("Raza activada exitosamente con ID: {}", razaActualizada.getId());
        return razaMapper.toDTO(razaActualizada);
    }

    @Override
    @Transactional
    public RazaDTO desactivarRaza(Long id) {
        log.info("Desactivando raza con ID: {}", id);

        Raza raza = razaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Raza no encontrada con ID: {}", id);
                    return new ResourceNotFoundException(String.format(RAZA_NO_ENCONTRADA, id));
                });

        raza.desactivar();
        Raza razaActualizada = razaRepository.save(raza);

        log.info("Raza desactivada exitosamente con ID: {}", razaActualizada.getId());
        return razaMapper.toDTO(razaActualizada);
    }

    @Override
    @Transactional
    public void eliminarRaza(Long id) {
        log.info("Eliminando raza con ID: {}", id);

        Raza raza = razaRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Raza no encontrada con ID: {}", id);
                    return new ResourceNotFoundException(String.format(RAZA_NO_ENCONTRADA, id));
                });

        // Nota: Si hay pacientes con esta raza, la eliminación fallará por FK constraint
        // En el futuro se puede agregar validación adicional aquí

        razaRepository.delete(raza);
        log.info("Raza eliminada exitosamente con ID: {}", id);
    }

    @Override
    public long contarRazasPorEspecie(TipoEspecie especie) {
        log.debug("Contando razas de especie: {}", especie);

        long cantidad = razaRepository.countByEspecie(especie);
        log.info("Se encontraron {} razas de especie {}", cantidad, especie);

        return cantidad;
    }

    @Override
    public Map<TipoEspecie, Long> obtenerEstadisticasPorEspecie() {
        log.debug("Generando estadísticas de razas por especie");

        Map<TipoEspecie, Long> estadisticas = Arrays.stream(TipoEspecie.values())
                .collect(Collectors.toMap(
                        especie -> especie,
                        razaRepository::countByEspecie
                ));

        log.info("Estadísticas generadas para {} especies", estadisticas.size());
        return estadisticas;
    }

    @Override
    public boolean existeRaza(String nombre, TipoEspecie especie) {
        return razaRepository.existsByNombreAndEspecie(nombre, especie);
    }
}
