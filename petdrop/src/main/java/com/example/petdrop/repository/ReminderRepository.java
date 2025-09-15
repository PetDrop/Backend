package com.example.petdrop.repository;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.petdrop.model.Reminder;

public interface ReminderRepository extends MongoRepository<Reminder, String> {
    @Query("{ 'nextRuns': { $lt: ?0 } }")
    List<Reminder> findDueReminders(ZonedDateTime time);
}

