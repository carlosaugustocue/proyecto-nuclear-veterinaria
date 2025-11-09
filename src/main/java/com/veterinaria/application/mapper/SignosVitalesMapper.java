package com.veterinaria.application.mapper;

import com.veterinaria.application.dto.medical.SignosVitalesDTO;
import com.veterinaria.application.dto.medical.SignosVitalesRequest;
import com.veterinaria.domain.entity.medical.SignosVitales;
import org.mapstruct.*;

/**
 * Mapper para convertir entre SignosVitales (Value Object) y DTOs.
 * Utiliza MapStruct para generación automática de código.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface SignosVitalesMapper {

    /**
     * Convierte SignosVitales a SignosVitalesDTO
     * @param signosVitales el value object
     * @return el DTO
     */
    @Mapping(target = "condicionCorporalDescripcion", expression = "java(signosVitales.evaluarCondicionCorporal())")
    @Mapping(target = "tieneParametrosAnormales", expression = "java(!signosVitales.sonNormalesParaPerro() && !signosVitales.sonNormalesParaGato())")
    @Mapping(target = "tieneFiebre", expression = "java(signosVitales.tieneFiebre())")
    @Mapping(target = "tieneHipotermia", expression = "java(signosVitales.tieneHipotermia())")
    SignosVitalesDTO toDTO(SignosVitales signosVitales);

    /**
     * Convierte SignosVitalesRequest a SignosVitales
     * @param request el DTO de creación
     * @return el value object
     */
    SignosVitales toEntity(SignosVitalesRequest request);

    /**
     * Actualiza SignosVitales con datos de SignosVitalesRequest
     * @param request el DTO con los datos a actualizar
     * @param signosVitales el value object a actualizar
     */
    void updateEntityFromDTO(SignosVitalesRequest request, @MappingTarget SignosVitales signosVitales);
}
