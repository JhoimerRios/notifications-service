package com.example.notifications_service.controller;

import com.example.notifications_service.client.ProductClient;
import com.example.notifications_service.dto.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final ProductClient productClient;

    public NotificationController(ProductClient productClient) {
        this.productClient = productClient;
    }

    @GetMapping("/product-info/{productId}")
    public Mono<ProductDto> getProductDetailsForNotification(@PathVariable Long productId) {
        // Llama al products-service para obtener detalles del producto
        // Esto simula que el servicio de notificaciones necesita la info de un producto
        return productClient.getProductById(productId);
    }

    @PutMapping("/update-product/{productId}")
    public Mono<ResponseEntity<ProductDto>> updateProductViaNotificationService(
            @PathVariable Long productId,
            @RequestBody ProductDto productDto) {
        // Llama al products-service para actualizar el producto
        return productClient.putProduct(productId, productDto)
                .map(updatedProduct -> new ResponseEntity<>(updatedProduct, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Maneja el caso de producto no encontrado
    }

    @PostMapping("/create-product")
    public Mono<ResponseEntity<ProductDto>> createProductViaNotificationService(@RequestBody ProductDto productDto) {
        // Llama al products-service para crear el producto, utilizando el método createProduct del cliente
        return productClient.createProduct(productDto)
                .map(newProduct -> new ResponseEntity<>(newProduct, HttpStatus.CREATED));
    }
}

