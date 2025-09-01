package com.example.notifications_service.client;

import com.example.notifications_service.dto.ProductDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {

    private final WebClient webClient;

    public ProductClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    public Mono<ProductDto> getProductById(Long id) {
        return this.webClient.get()
                .uri("/api/v1/products/{id}", id)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Mono<ProductDto> putProduct(Long id, ProductDto productDto) {
        return this.webClient.put()
                .uri("/api/v1/products/{id}", id)
                .body(Mono.just(productDto), ProductDto.class)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    // Nuevo método para crear un producto en el products-service
    public Mono<ProductDto> createProduct(ProductDto productDto) {
        return this.webClient.post() // Usa el método POST
                .uri("/api/v1/products") // URI del endpoint de creación en products-service
                .body(Mono.just(productDto), ProductDto.class) // Envía el DTO en el cuerpo
                .retrieve()
                .bodyToMono(ProductDto.class);
    }
}
