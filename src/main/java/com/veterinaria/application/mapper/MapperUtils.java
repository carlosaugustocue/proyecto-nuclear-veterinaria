package com.veterinaria.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * Utilidades compartidas para los mappers.
 * Contiene métodos comunes reutilizables por todos los mappers.
 *
 * @author Sistema Veterinaria
 */
@Mapper(componentModel = "spring")
public interface MapperUtils {

    /**
     * Convierte TipoEspecie enum a String
     * @param especie el enum de especie
     * @return el nombre del enum como String
     */
    @Named("especieToString")
    default String especieToString(com.veterinaria.domain.enums.TipoEspecie especie) {
        return especie != null ? especie.name() : null;
    }
}
