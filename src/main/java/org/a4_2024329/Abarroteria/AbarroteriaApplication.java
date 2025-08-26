package org.a4_2024329.Abarroteria;

import org.a4_2024329.Abarroteria.Entity.Producto;
import org.a4_2024329.Abarroteria.Service.IProductoService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;


@SpringBootApplication
public class AbarroteriaApplication implements CommandLineRunner {
    @Autowired
    private IProductoService productoService;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(AbarroteriaApplication.class);
    String salto = System.lineSeparator();

    public static void main(String[] args) {

        logger.info("Iniciamos la aplicacion");
        SpringApplication.run(AbarroteriaApplication.class, args);
        logger.info("Aplicacion Finalizada");
    }

    @Override
    public void run(String... args) throws Exception {
        registroProductoApp();
    }

    private void registroProductoApp(){
        logger.info("+++++ Bienvenido a la aplicacion de Registro Productos +++++");
        var salir = false;
        var consola = new Scanner(System.in);
        while (!salir){
            var opcion = mostrarMenu(consola);
            salir = ejecutarOpciones(consola, opcion);
            logger.info(salto);
        }
    }

    private int mostrarMenu(Scanner consola){
        logger.info("""
             ***Aplicacion***
             1.Listar Productos
             2.Buscar Productos
             3.Agregar Productos
             4.Modificar Productos
             5.Eliminar Productos
             6.Salir
             """);
        var opcion = Integer.parseInt(consola.nextLine());
        return opcion;
    }

    private boolean ejecutarOpciones(Scanner consola, int opcion){
        var salir = false;
        switch (opcion){
            case 1 -> {
                logger.info(salto+"*** Lista de Productos***"+salto);
                List<Producto> productos = productoService.listarProductos();
                productos.forEach(producto -> logger.info(producto.toString()+salto));
            }
            case 2 -> {
                logger.info(salto+"*** Buscar Producto por su codigo ***"+salto);
                var codigo = Integer.parseInt(consola.nextLine());
                Producto producto = productoService.buscarProductoPorId(codigo);
                if(producto != null){
                    logger.info("Cliente encontrado: " + producto + salto);
                }else {
                    logger.info("Cliente No encontrado " + producto + salto);
                }
            }
            case 3 -> {
                logger.info(salto+"***Agregar Producto***"+salto);
                logger.info("Ingrese el Id de la categoria al la que pertenece: ");
                var idCategoria = consola.nextLine();
                logger.info("Ingrese el nombre del Producto ");
                var nombre = consola.nextLine();
                logger.info("Ingrese la marca del Producto ");
                var marca = consola.nextLine();
                logger.info("Ingrese la descripcion del Producto ");
                var descripcion = consola.nextLine();
                logger.info("Ingrese el precio del producto ");
                var precio = consola.nextLine();
                var producto = new Producto();
                producto.setIdCategoria(Integer.parseInt(idCategoria));
                producto.setNombreProducto(nombre);
                producto.setMarca(marca);
                producto.setDescripcion(descripcion);
                producto.setPrecio(Integer.valueOf(precio));
                productoService.guardarProducto(producto);
                logger.info("Producto Agregado" + producto + salto);
            }

            case 4 -> {
                logger.info(salto+"*** Modificar Producto ***"+salto);
                logger.info("Agregue el codigo del producto a modificar");
                var codigo = Integer.parseInt(consola.nextLine());
                Producto producto = productoService.buscarProductoPorId(codigo);
                if (producto != null){
                    logger.info("Ingrese el Id de la categoria al la que pertenece: ");
                    var idCategoria = consola.nextLine();
                    logger.info("Ingrese el nombre del Producto ");
                    var nombre = consola.nextLine();
                    logger.info("Ingrese la marca del Producto ");
                    var marca = consola.nextLine();
                    logger.info("Ingrese la descripcion del Producto ");
                    var descripcion = consola.nextLine();
                    logger.info("Ingrese el precio del producto ");
                    var precio = consola.nextLine();

                    producto.setIdCategoria(Integer.parseInt(idCategoria));
                    producto.setNombreProducto(nombre);
                    producto.setMarca(marca);
                    producto.setDescripcion(descripcion);
                    producto.setPrecio(Integer.parseInt(precio));


                    productoService.guardarProducto(producto);

                    logger.info(" Producto actualizado con exito: " + producto + salto);
                } else {
                    logger.info(" Producto No Encontrado" + salto);
                }
            }

            case 5 -> {
                logger.info(salto+ "*** Eliminar Producto ***");
                logger.info("Ingrese el codigo del Cliente a eliminar");
                var codigo = Integer.parseInt(consola.nextLine());
                var producto = productoService.buscarProductoPorId(codigo);
                if (producto != null){
                    productoService.eliminarProducto(producto);
                    logger.info("Cliente eliminado con exito" + producto + salto);
                }else{
                    logger.info("Cliente NO encontrado " + producto + salto);
                }
            }
            case 6 ->{
                logger.info("Gracias por visitar" + salto + salto);
                salir = true;
            }
            default -> logger.info("Opcion invalidad");
        }
        return salir;
    }
}
