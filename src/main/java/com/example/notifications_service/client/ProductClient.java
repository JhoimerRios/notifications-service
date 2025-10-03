package com.example.notifications_service.client;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.notifications_service.dto.CustomPageDto;
import com.example.notifications_service.dto.ProductDto;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductClient {

    private final WebClient webClient;

    public Mono<ProductDto> createProduct(ProductDto productDto) {
        return webClient.post()
                .uri("/products")
                .bodyValue(productDto)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Mono<ProductDto> getProductById(Long id) {
        return webClient.get()
                .uri("/products/{id}", id)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Mono<ProductDto> putProduct(Long id, ProductDto productDto) {
        return webClient.put()
                .uri("/products/{id}", id)
                .bodyValue(productDto)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }

    public Mono<Void> deleteProduct(Long id) {
        return webClient.delete()
                .uri("/products/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Error: Could not delete product.")))
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Error: Server error while deleting product.")))
                .bodyToMono(Void.class);
    }

    public Mono<CustomPageDto<ProductDto>> getAllProducts(int page, int size) {
        return webClient.get()
                .uri("/products?page={page}&size={size}", page, size)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> Mono.error(new RuntimeException("Error: Could not retrieve products.")))
                .bodyToMono(new ParameterizedTypeReference<CustomPageDto<ProductDto>>() {});
    }
}