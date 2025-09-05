package com.example.notifications_service.controller;

import com.example.notifications_service.client.ProductClient;
import com.example.notifications_service.dto.CustomPageDto;
import com.example.notifications_service.dto.ProductDto;
import com.example.notifications_service.model.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final ProductClient productClient;
    private final NotificationService notificationService;

    @Autowired
    public NotificationController(ProductClient productClient, NotificationService notificationService) {
        this.productClient = productClient;
        this.notificationService = notificationService;
    }

    @GetMapping("/product-info/{id}")
    public Mono<ResponseEntity<ProductDto>> getProductInfo(@PathVariable Long id) {
        return productClient.getProductById(id)
                .map(productDto -> new ResponseEntity<>(productDto, HttpStatus.OK));
    }

    @PostMapping("/create-product")
    public Mono<ResponseEntity<ProductDto>> createProductViaNotificationService(@RequestBody ProductDto productDto) {
        return productClient.createProduct(productDto)
                .map(newProduct -> {
                    // Guarda la notificación después de la creación exitosa del producto
                    notificationService.saveNotification(
                            "PRODUCT_CREATED",
                            "Producto '" + newProduct.getName() + "' creado con éxito.",
                            newProduct.getId()
                    );
                    return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
                });
    }

    @PutMapping("/update-product/{id}")
    public Mono<ResponseEntity<ProductDto>> updateProductViaNotificationService(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return productClient.putProduct(id, productDto)
                .map(updatedProduct -> {
                    // Guarda la notificación después de la creación exitosa del producto
                    notificationService.saveNotification(
                            "PRODUCT_UPDATED",
                            "Producto '" + updatedProduct.getName() + "' modificado con éxito.",
                            updatedProduct.getId()
                    );
                    return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
                });
    }

    @DeleteMapping("/delete-product/{id}")
    public Mono<ResponseEntity<Void>> deleteProductViaNotificationService(@PathVariable Long id) {
        return productClient.deleteProduct(id)
                .then(Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
    }

    @GetMapping("/products-paged")
    public Mono<ResponseEntity<CustomPageDto<ProductDto>>> getProductsPaged(Pageable pageable) {
        return productClient.getAllProducts(pageable.getPageNumber(), pageable.getPageSize())
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK));
    }
}
