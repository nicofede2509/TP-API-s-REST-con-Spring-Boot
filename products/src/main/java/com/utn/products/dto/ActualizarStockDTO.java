package com.utn.products.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ActualizarStockDTO {
    @NotNull(message = "Por favor, ingrese un valor.")
    @Min(value = 0, message = "Ingrese al menos el valor nulo.")
    private Integer stock;
}
