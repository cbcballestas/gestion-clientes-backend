package com.cballestas.springboot.backend.apirest.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cballestas.springboot.backend.apirest.model.Cliente;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

	@Query(value = "SELECT * from clientes WHERE email = ?1 and id <> ?2", nativeQuery = true)
	Optional<Cliente> findByEmailAndId(String email, Long id);
}
