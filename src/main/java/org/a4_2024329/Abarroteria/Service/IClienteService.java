package org.a4_2024329.Abarroteria.Service;

import org.a4_2024329.Abarroteria.Entity.Cliente;

import java.util.List;

public interface IClienteService {
    List<Cliente> listarClientes();
    void guardarCliente(Cliente cliente);
    Cliente login(String email, String contrasena);
}
