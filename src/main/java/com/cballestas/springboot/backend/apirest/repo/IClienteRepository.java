package com.cballestas.springboot.backend.apirest.repo;

import com.cballestas.springboot.backend.apirest.model.Cliente;
import com.cballestas.springboot.backend.apirest.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

    @Query(value = "SELECT * FROM regiones", nativeQuery = true)
    List<Region> getAllRegions();

    @Query(value = "SELECT * from clientes WHERE email = ?1 and id <> ?2", nativeQuery = true)
    Optional<Cliente> findByEmailAndId(String email, Long id);
}
