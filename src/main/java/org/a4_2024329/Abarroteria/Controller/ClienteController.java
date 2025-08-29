package org.a4_2024329.Abarroteria.Controller;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Data;
import org.a4_2024329.Abarroteria.Entity.Cliente;
import org.a4_2024329.Abarroteria.Service.IClienteService;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Component
@Data
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    private List<Cliente> clientes;
    private Cliente clienteSeleccionado; // usado en registro
    private Cliente clienteLogin;        // usado en login

    private static final Logger LOGGER = Logger.getLogger(ClienteController.class.getName());

    @PostConstruct
    public void init() {
        cargarDatos();
        this.clienteSeleccionado = new Cliente();
        this.clienteLogin = new Cliente();
    }

    public void cargarDatos() {
        this.clientes = this.clienteService.listarClientes();
        if (this.clientes != null) {
            this.clientes.forEach(cliente -> LOGGER.info(cliente.toString()));
        }
    }

    public void guardarCliente() {
        boolean esNuevo = (this.clienteSeleccionado.getIdCliente() == null);

        if (esNuevo) {
            this.clienteSeleccionado.setFechaRegistro(LocalDate.now());
            this.clienteSeleccionado.setActivo(true); // Siempre activo al crear
        }

        this.clienteService.guardarCliente(this.clienteSeleccionado);

        if (esNuevo) {
            this.clientes.add(this.clienteSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Cliente registrado"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Cliente actualizado"));
        }

        PrimeFaces.current().executeScript("PF('ventanaModalRegistro').hide()");
        PrimeFaces.current().ajax().update("form-registro", "form-login");

        this.clienteSeleccionado = new Cliente();
    }

    public void login() {
        Cliente cliente = this.clienteService.login(
                this.clienteLogin.getCorreo(),
                this.clienteLogin.getContrasena()
        );

        if (cliente != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Bienvenido", cliente.getNombre() + " " + cliente.getApellido()));

            LOGGER.info("Login exitoso: " + cliente.getCorreo());

            PrimeFaces.current().executeScript("PF('ventanaModalSesion').hide()");

            try {
                FacesContext.getCurrentInstance().getExternalContext()
                        .redirect("index.xhtml");
            } catch (Exception e) {
                LOGGER.severe("Error al redirigir: " + e.getMessage());
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", "Correo o contraseña incorrectos"));
        }

        this.clienteLogin = new Cliente();
    }

}
