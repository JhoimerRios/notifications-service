package com.example.notifications_service.repository;

import com.example.notifications_service.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad Notification.
 * Extiende de JpaRepository para heredar métodos CRUD automáticos.
 */
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
