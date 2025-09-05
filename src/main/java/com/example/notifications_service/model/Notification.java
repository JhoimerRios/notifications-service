package com.example.notifications_service.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Clase de entidad que representa una notificación.
 * Esta clase se mapea a una tabla en la base de datos de PostgreSQL.
 */
@Entity
@Data
public class Notification {

    /**
     * El identificador único de la notificación.
     * Se genera automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * El tipo de notificación (ej. "PRODUCT_CREATED", "PRODUCT_UPDATED").
     */
    private String type;

    /**
     * El mensaje detallado de la notificación.
     */
    private String message;

    /**
     * La fecha y hora en que se creó la notificación.
     */
    private LocalDateTime timestamp;

    /**
     * El ID del producto al que se refiere esta notificación.
     * Esto crea una relación lógica con el Products-Service sin una clave foránea real en la base de datos.
     */
    private Long productId;
}