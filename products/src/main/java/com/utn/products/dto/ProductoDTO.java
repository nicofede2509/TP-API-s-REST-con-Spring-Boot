package com.utn.products.dto;


import com.utn.products.model.Categoria;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductoDTO {

    @Size(min = 3, max = 100, message = "El nombre debe tener al menos 3 carácteres y 100 de máximo.")
    @NotNull(message = "El nombre no puede ser vacío.")
    @NotBlank(message = "Campo obligatorio.")
    private String nombre;

    @Size(max = 500, message = "Límite alcanzado.")
    private String descripcion;

    @NotNull(message = "El precio debe tener un valor numérico.")
    @DecimalMin(value = "0.01", message = "El precio debe ser de al menos 0.01")
    private Double precio;

    @NotNull(message = "Ingrese un valor por favor.")
    @DecimalMin(value = "0", message = "El stock debe ser de mínimo 0.")
    private int stock;

    private Categoria categoria;

}
