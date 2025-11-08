package com.utn.products.service;

import com.utn.products.dto.ProductoDTO;
import com.utn.products.dto.ProductoResponseDTO;
import com.utn.products.model.Categoria;
import com.utn.products.model.Producto;
import com.utn.products.repository.ProductoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;

    public ProductoService(ProductoRepository productoRepository, ModelMapper modelMapper) {
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
    }

    private ProductoResponseDTO convertirAResponse(Producto producto) {
        return modelMapper.map(producto, ProductoResponseDTO.class);
    }

    private Producto convertirADominio(ProductoDTO dto) {
        return modelMapper.map(dto, Producto.class);
    }

    public ProductoResponseDTO crearProducto(ProductoDTO productoDto) {
        Producto producto = convertirADominio(productoDto);
        producto = productoRepository.save(producto);
        return convertirAResponse(producto);
    }

    public void crearProducto(Producto producto){
        productoRepository.save(producto);
    }

    public List<ProductoResponseDTO> obtenerProductos(){
        List<Producto> productos = productoRepository.findAll();
        return productos.stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public Optional<ProductoResponseDTO> obtenerPorId(Long idABuscar){
        Optional<Producto> productoOptional = productoRepository.findById(idABuscar);
        return productoOptional.map(this::convertirAResponse);
    }

    public List<ProductoResponseDTO> obtenerPorCategoria(Categoria categoria){
        List<Producto> productos = productoRepository.findByCategoria(categoria);
        return productos.stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public ProductoResponseDTO actualizarProducto(Long id, ProductoDTO productoActualizado){
        Optional<Producto> productoOptional =productoRepository.findById((id));
        if(productoOptional.isPresent()){
            Producto productoExistente = productoOptional.get();
            productoExistente.setNombre(productoActualizado.getNombre());
            productoExistente.setDescripcion(productoActualizado.getDescripcion());
            productoExistente.setPrecio(productoActualizado.getPrecio());
            productoExistente.setStock(productoActualizado.getStock());
            productoExistente.setCategoria(productoActualizado.getCategoria());

            System.out.println("Producto actualizado exitosamente.");
        }else{
            System.out.println("El producto no existe.");
        }
        return modelMapper.map(productoActualizado, ProductoResponseDTO.class);
    }

    public ProductoResponseDTO actualizarStock(Long id, Integer nuevoStock){
        Optional<Producto> productoOptional = productoRepository.findById((id));
        if(productoOptional.isPresent()){
            Producto productoExistente = productoOptional.get();
            productoExistente.setStock(nuevoStock);

            System.out.println("Producto actualizado exitosamente.");
        }else{
            System.out.println("El producto no existe.");
        }
        return modelMapper.map(productoOptional, ProductoResponseDTO.class);
    }

    public void eliminarProducto(Long id){
        if(productoRepository.existsById(id)){
            productoRepository.deleteById(id);
            System.out.println("Producto eliminado con éxito.");
        }else {
            System.out.println("El producto no existe");
        }
    }


}
