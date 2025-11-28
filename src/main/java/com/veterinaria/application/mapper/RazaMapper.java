package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.patients.RazaDTO;
import com.veterinaria.domain.entity.patients.Raza;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre entidad Raza y DTOs.
 *
 * @author Sistema Veterinaria
 */
@Component
public class RazaMapper {

    /**
     * Convierte una entidad Raza a RazaDTO
     *
     * @param raza entidad Raza
     * @return RazaDTO
     */
    public RazaDTO toDTO(Raza raza) {
        if (raza == null) {
            return null;
        }

        return RazaDTO.builder()
                .id(raza.getId())
                .nombre(raza.getNombre())
                .especie(raza.getEspecie())
                .descripcion(raza.getDescripcion())
                .esPredefinida(raza.getEsPredefinida())
                .esMestizo(raza.getEsMestizo())
                .tamanioTipico(raza.getTamanioTipico())
                .pesoPromedioKg(raza.getPesoPromedioKg())
                .isActive(raza.getIsActive())
                .createdAt(raza.getCreatedAt())
                .updatedAt(raza.getUpdatedAt())
                .createdBy(raza.getCreatedBy())
                .updatedBy(raza.getUpdatedBy())
                .build();
    }
}
