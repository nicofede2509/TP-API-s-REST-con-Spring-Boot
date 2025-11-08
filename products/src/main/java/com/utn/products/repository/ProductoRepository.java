package com.utn.products.repository;

import com.utn.products.model.Categoria;
import com.utn.products.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto,Long> {
    List<Producto> findByCategoria(Categoria categoria);
}
