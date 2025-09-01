package com.example.notifications_service.dto;

import lombok.Data;

// ProductDto representa la estructura de los datos de un producto
// que el servicio de notificaciones espera recibir del products-service.
@Data
public class ProductDto {
    private Long id; // El ID del producto, crucial para identificar el producto recibido.
    private String name;
    private double price;
    private String category;
}
