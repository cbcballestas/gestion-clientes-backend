package com.cballestas.springboot.backend.apirest.service.impl;

import com.cballestas.springboot.backend.apirest.dto.RegionDTO;
import com.cballestas.springboot.backend.apirest.repo.IRegionRepository;
import com.cballestas.springboot.backend.apirest.service.IRegionService;
import com.cballestas.springboot.backend.apirest.util.ConverterUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RegionServiceImpl implements IRegionService {

    private final IRegionRepository regionRepository;
    private final ConverterUtil converterUtil;


    public RegionServiceImpl(IRegionRepository regionRepository, ConverterUtil converterUtil) {
        this.regionRepository = regionRepository;
        this.converterUtil = converterUtil;
    }

    /**
     * Método que se encarga de retornar todas las regiones registradas
     *
     * @return lista de tipo RegionDTO
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<RegionDTO>> getAllRegions() {
        log.info("Inicia carga listado de regiones");

        List<RegionDTO> regiones = regionRepository.findAll()
                .stream().map(region -> converterUtil.mapearDTO(region, RegionDTO.class))
                .collect(Collectors.toList());

        log.info("Finaliza carga listado de regiones");
        return ResponseEntity.ok(regiones);
    }
}
