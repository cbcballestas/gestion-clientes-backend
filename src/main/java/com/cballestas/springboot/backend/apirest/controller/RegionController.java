package com.cballestas.springboot.backend.apirest.controller;

import com.cballestas.springboot.backend.apirest.dto.RegionDTO;
import com.cballestas.springboot.backend.apirest.service.IRegionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regiones")
@CrossOrigin(origins = {"http://localhost:4200"})
public class RegionController {

    private final IRegionService regionService;

    public RegionController(IRegionService regionService) {
        this.regionService = regionService;
    }

    @GetMapping
    public ResponseEntity<List<RegionDTO>> getAllRegions() {
        return regionService.getAllRegions();
    }
}
