package com.utn.products.controller;

import com.utn.products.dto.ActualizarStockDTO;
import com.utn.products.dto.ProductoDTO;
import com.utn.products.dto.ProductoResponseDTO;
import com.utn.products.model.Categoria;
import com.utn.products.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "App de Gestión de Productos.")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Obtiene la lista de todos los productos existentes.")
    @ApiResponse(responseCode = "200", description = "Lista obtenida de forma exitosa.")
    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> obtenerTodos(){
        List<ProductoResponseDTO> productos = productoService.obtenerProductos();
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Obtener un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto encontrado."),
            @ApiResponse(responseCode = "404", description = "Producto inexistente.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> obtenerPorId(@PathVariable Long id){
        Optional<ProductoResponseDTO> productoDtoOptional = productoService.obtenerPorId(id);
        return productoDtoOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtener productos por categoría.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista filtrada exitosamente."),
            @ApiResponse(responseCode = "400", description = "Ningún producto encontrado para la categoría ingresada.")
    })
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoResponseDTO>> obtenerPorCategoria(@PathVariable("categoria") Categoria categoria){
        List<ProductoResponseDTO> productoPorCategoria = productoService.obtenerPorCategoria(categoria);
        return ResponseEntity.ok(productoPorCategoria);
    }

    @Operation(summary = "Crear un nuevo producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos (ej. nombre vacío, precio negativo)")
    })
    @PostMapping
    public ResponseEntity<ProductoResponseDTO> crearProducto(@Valid @RequestBody ProductoDTO productoDTO){
        ProductoResponseDTO productoACrear = productoService.crearProducto(productoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoACrear);
    }

    @Operation(summary = "Actualizar un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Producto actualizado."),
            @ApiResponse(responseCode = "404", description = "Producto inexistente."),
            @ApiResponse(responseCode = "400", description = "Campo/s inválido/s.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponseDTO> actualizarProducto(@PathVariable Long id, @Valid @RequestBody ProductoDTO productoDTO){
        ProductoResponseDTO productoActualizado = productoService.actualizarProducto(id, productoDTO);
        return ResponseEntity.ok(productoActualizado);
    }

    @Operation(summary = "Actualizar stock de un producto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stock de producto actualizado."),
            @ApiResponse(responseCode = "404", description = "Producto inexistente."),
            @ApiResponse(responseCode = "400", description = "Campo/s inválido/s")
    })
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponseDTO> actualizarStock(@PathVariable Long id, @Valid @RequestBody ActualizarStockDTO actualizarStockDTO){
        ProductoResponseDTO productoActualizado = productoService.actualizarStock(id, actualizarStockDTO.getStock());
        return ResponseEntity.ok(productoActualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Producto eliminado."),
            @ApiResponse(responseCode = "404", description = "Producto inexistente.")
    })
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id){
        productoService.eliminarProducto(id);
        return ResponseEntity.noContent().build();
    }
}