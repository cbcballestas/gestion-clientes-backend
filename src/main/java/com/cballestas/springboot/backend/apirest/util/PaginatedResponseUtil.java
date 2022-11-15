package com.cballestas.springboot.backend.apirest.util;

import java.util.List;

import org.springframework.data.domain.Page;

import com.cballestas.springboot.backend.apirest.dto.PaginatedResponseDTO;

public class PaginatedResponseUtil {

    private PaginatedResponseUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static <P, T> PaginatedResponseDTO<T> buildResponse(Page<P> page, List<T> lista) {

        PaginatedResponseDTO<T> paginatedResponseDTO = new PaginatedResponseDTO<>();
        paginatedResponseDTO.setData(lista);
        paginatedResponseDTO.setEmpty(page.isEmpty());
        paginatedResponseDTO.setFirst(page.isFirst());
        paginatedResponseDTO.setLast(page.isLast());
        paginatedResponseDTO.setNumber(page.getNumber());
        paginatedResponseDTO.setSize(page.getSize());
        paginatedResponseDTO.setTotalElements(page.getTotalElements());
        paginatedResponseDTO.setTotalPages(page.getTotalPages());


        return paginatedResponseDTO;
    }

}
