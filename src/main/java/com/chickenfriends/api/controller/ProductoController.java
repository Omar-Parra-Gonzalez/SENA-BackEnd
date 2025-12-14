package com.chickenfriends.api.controller;

import com.chickenfriends.api.model.Producto;
import com.chickenfriends.api.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;


import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Producto obtenerProductoPorId(@PathVariable long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    @PostMapping
    public String guardarProducto(@RequestBody Producto producto) {
        productoRepository.save(producto);
        return "Producto guardado";
    }

    @PutMapping("/{id}")
    public String actualizarProducto(@PathVariable long id, @RequestBody Producto producto) {

        Producto actualizar = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        actualizar.setProducto(producto.getProducto());
        actualizar.setPrecio(producto.getPrecio());
        actualizar.setCantidad(producto.getCantidad());

        productoRepository.save(actualizar);
        return "Producto actualizado";
    }

    @PutMapping("/descontar/{id}")
    public ResponseEntity<String> descontarInventario(
            @PathVariable long id,
            @RequestBody Map<String, Integer> body) {

        int cantidad = body.get("cantidad");
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        if (producto.getCantidad() < cantidad) {
            return ResponseEntity.badRequest().body("Inventario insuficiente");
        }
        producto.setCantidad(producto.getCantidad() - cantidad);
        productoRepository.save(producto);
        return ResponseEntity.ok("Inventario actualizado");
    }

    @DeleteMapping("/{id}")
    public String eliminarProducto(@PathVariable long id) {
        productoRepository.deleteById(id);
        return "Producto eliminado";
    }

    @GetMapping("/{id}/disponible")
    public ResponseEntity<Integer> verificarInventario(@PathVariable Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        return ResponseEntity.ok(producto.getCantidad());
    }
}
