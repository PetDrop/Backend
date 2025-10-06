package com.example.petdrop.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.petdrop.model.DatabaseNotification;
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
        List<DatabaseNotification> dueNotifs = repo.findDueNotifications(curTime);
        if (dueNotifs.isEmpty()) {
            return;
        }

        // send all due notifications in chunks
        expoPushService.sendPushBatch(dueNotifs);

        List<DatabaseNotification> toDelete = new ArrayList<>();
        List<DatabaseNotification> toUpdate = new ArrayList<>();

        // update scheduling info
        for (DatabaseNotification n : dueNotifs) {
            boolean notifToBeDeleted = true;
            if (n.getRepeatInterval() != 0) {
                Instant[] nextRuns = n.getNextRuns();
                for (int i = 0; i < nextRuns.length; i++) {
                    if (nextRuns[i].isBefore(curTime)) {
                        if (nextRuns[i].isBefore(n.getFinalRuns()[i])) {
                            nextRuns[i] = nextRuns[i].plusSeconds(n.getRepeatInterval() * 60);
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
