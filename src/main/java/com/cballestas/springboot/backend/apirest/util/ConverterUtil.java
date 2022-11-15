package com.cballestas.springboot.backend.apirest.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ConverterUtil {

    private final ModelMapper modelMapper;


    public ConverterUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Método que se encarga de realizar el mapeo de entidad a DTO
     *
     * @param object Objeto a transformar
     * @param clazz  Clase de tipo DTO
     * @param <C>    Tipo entidad
     * @param <D>    Tipo DTO
     * @return Objeto de tipo DTO
     */
    public <C, D> D mapearDTO(C object, Class<D> clazz) {
        return modelMapper.map(object, clazz);
    }

    /**
     * Método que se encarga de realizar el mapeo de entidad a DTO
     *
     * @param object Objeto a transformar
     * @param clazz  Clase de tipo entidad
     * @param <C>    Tipo entidad
     * @param <D>    Tipo DTO
     * @return Objeto de tipo entidad
     */
    public <C, D> C mapearEntidad(D object, Class<C> clazz) {
        return modelMapper.map(object, clazz);
    }
}
