package com.example.petdrop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.petdrop.model.Notification;
import com.example.petdrop.repository.NotificationRepository;

@Service
public class NotificationScheduler {

    private final NotificationRepository repo;
    private final ExpoPushService expoPushService;

    public NotificationScheduler(NotificationRepository repo, ExpoPushService expoPushService) {
        this.repo = repo;
        this.expoPushService = expoPushService;
    }

    @Scheduled(fixedRate = 60000)
    public void processNotifications() {
        List<Notification> dueNotifs = repo.findDueNotifications(LocalDateTime.now());
        if (dueNotifs.isEmpty()) {
            return;
        }

        // send all due notifications in chunks
        expoPushService.sendPushBatch(dueNotifs);

        // update scheduling info
        for (Notification n : dueNotifs) {
            if (n.getRepeatInterval() != null && (n.getRemainingRepeats() == null || n.getRemainingRepeats() > 1)) {
                n.setNextRun(n.getNextRun().plus(n.getRepeatInterval()));
                if (n.getRemainingRepeats() != null) {
                    n.setRemainingRepeats(n.getRemainingRepeats() - 1);
                }
            } else {
                n.setActive(false);
            }
        }
        repo.saveAll(dueNotifs);
    }

}
