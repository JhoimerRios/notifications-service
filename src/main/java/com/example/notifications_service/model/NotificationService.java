package com.example.notifications_service.model;

import com.example.notifications_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Servicio para gestionar la lógica de negocio de las notificaciones.
 */
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    /**
     * Guarda una notificación en la base de datos.
     * @param type El tipo de notificación (ej. "PRODUCT_CREATED").
     * @param message El mensaje de la notificación.
     * @param productId El ID del producto relacionado.
     */
    public void saveNotification(String type, String message, Long productId) {
        Notification notification = new Notification();
        notification.setType(type);
        notification.setMessage(message);
        notification.setTimestamp(LocalDateTime.now());
        notification.setProductId(productId);
        notificationRepository.save(notification);
        System.out.println("Notificación guardada: " + message);
    }
}
