package org.a4_2024329.Abarroteria.Repository;

import org.a4_2024329.Abarroteria.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Integer> {
    Optional<Cliente> findByCorreoAndContrasena(String correo, String contrasena);
}
