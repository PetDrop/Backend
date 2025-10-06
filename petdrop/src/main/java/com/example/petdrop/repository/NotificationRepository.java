package com.example.petdrop.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.petdrop.model.DatabaseNotification;

public interface NotificationRepository extends MongoRepository<DatabaseNotification, String> {
    @Query("{ 'nextRuns': { $lt: ?0 } }")
    List<DatabaseNotification> findDueNotifications(Instant time);
}

