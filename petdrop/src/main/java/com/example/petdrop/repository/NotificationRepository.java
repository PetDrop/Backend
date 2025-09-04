package com.example.petdrop.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.petdrop.model.Notification;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    @Query("{ 'active': true, 'nextRun': { $lt: ?0 } }")
    List<Notification> findDueNotifications(LocalDateTime time);
}

