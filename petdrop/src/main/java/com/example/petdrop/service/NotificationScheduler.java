package com.example.petdrop.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
        Instant curTime = Instant.now();
        List<Notification> dueNotifs = repo.findDueNotifications(curTime);
        if (dueNotifs.isEmpty()) {
            return;
        }

        // send all due notifications in chunks
        expoPushService.sendPushBatch(dueNotifs);

        List<Notification> toDelete = new ArrayList<>();
        List<Notification> toUpdate = new ArrayList<>();

        // update scheduling info
        for (Notification n : dueNotifs) {
            boolean notifToBeDeleted = true;
            String repeatInterval = n.getRepeatInterval();
            if (repeatInterval != null && !repeatInterval.isEmpty()) {
                Instant[] nextRuns = n.getNextRuns();
                for (int i = 0; i < nextRuns.length; i++) {
                    if (nextRuns[i].isBefore(curTime)) {
                        if (nextRuns[i].isBefore(n.getFinalRuns()[i])) {
                            // Add appropriate time interval based on string value
                            switch (repeatInterval) {
                                case "daily":
                                    nextRuns[i] = nextRuns[i].plus(1, ChronoUnit.DAYS);
                                    break;
                                case "weekly":
                                    nextRuns[i] = nextRuns[i].plus(1, ChronoUnit.WEEKS);
                                    break;
                                case "monthly":
                                    nextRuns[i] = nextRuns[i].plus(1, ChronoUnit.MONTHS);
                                    break;
                                default:
                                    // Unknown interval
                                    break;
                            }
                            n.setNextRuns(nextRuns);
                            notifToBeDeleted = false;
                        }
                        break;
                    }
                }
            }
            if (notifToBeDeleted) {
                toDelete.add(n);
            } else {
                toUpdate.add(n);
            }
        }
        if (!toDelete.isEmpty()) {
        repo.deleteAll(toDelete);
        }
        if (!toUpdate.isEmpty()) {
        repo.saveAll(toUpdate);
        }
    }

}
