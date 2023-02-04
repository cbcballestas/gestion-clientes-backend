package com.cballestas.springboot.backend.apirest.service;

import com.cballestas.springboot.backend.apirest.dto.RegionDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRegionService {
    ResponseEntity<List<RegionDTO>> getAllRegions();
}
