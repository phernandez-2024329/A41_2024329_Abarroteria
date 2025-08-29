package org.a4_2024329.Abarroteria.Service;
import org.a4_2024329.Abarroteria.Entity.Producto;
import java.util.List;

public interface IProductoService {
    public List<Producto> listarProductos();
    public Producto buscarProductoPorId(Integer codigo);
    public void guardarProducto(Producto producto);
    public void eliminarProducto(Producto producto);
}
