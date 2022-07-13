package com.practicas.springboot.backend.apirest.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.practicas.springboot.backend.apirest.model.Cliente;

@Repository
public interface IClienteRepo extends JpaRepository<Cliente, Long> {

	@Query(value = "SELECT * from clientes WHERE email = :email and id <> :idRegistro", nativeQuery = true)
	Cliente findByEmail(@Param("email") String email, @Param("idRegistro") Long idRegistro);
}
