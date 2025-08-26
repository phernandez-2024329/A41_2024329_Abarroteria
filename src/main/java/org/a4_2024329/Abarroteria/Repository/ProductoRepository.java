package org.a4_2024329.Abarroteria.Repository;

import org.a4_2024329.Abarroteria.Entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository <Producto, Integer> {
}
