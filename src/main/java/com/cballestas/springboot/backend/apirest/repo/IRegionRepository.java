package com.cballestas.springboot.backend.apirest.repo;

import com.cballestas.springboot.backend.apirest.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRegionRepository extends JpaRepository<Region, Long> {
}
