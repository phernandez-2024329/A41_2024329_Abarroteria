package org.a4_2024329.Abarroteria.Service;

import org.a4_2024329.Abarroteria.Entity.Cliente;
import org.a4_2024329.Abarroteria.Repository.IClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    @Override
    public Cliente login(String correo, String contrasena) {
        return clienteRepository.findByCorreoAndContrasena(correo, contrasena).orElse(null);
    }
}
