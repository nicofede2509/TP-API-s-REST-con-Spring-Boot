package com.utn.products.dto;

import com.utn.products.model.Categoria;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductoResponseDTO {
    private Long id;

    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;

    private Categoria categoria;
}
