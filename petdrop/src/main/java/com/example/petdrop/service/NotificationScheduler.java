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
    private final NotificationSharingService sharingService;

    public NotificationScheduler(NotificationRepository repo, ExpoPushService expoPushService, 
                                NotificationSharingService sharingService) {
        this.repo = repo;
        this.expoPushService = expoPushService;
        this.sharingService = sharingService;
    }

    @Scheduled(fixedRate = 60000)
    public void processNotifications() {
        Instant curTime = Instant.now();
        List<Notification> dueNotifs = repo.findDueNotifications(curTime);
        if (dueNotifs.isEmpty()) {
            return;
        }

        // Send notifications to all recipients (owner + shared users)
        for (Notification notif : dueNotifs) {
            List<String> recipients = sharingService.findAllRecipients(notif.getOwnerUsername());
            if (!recipients.isEmpty()) {
                expoPushService.sendPushToMultipleRecipients(notif, recipients);
            }
        }

        List<Notification> toDelete = new ArrayList<>();
        List<Notification> toUpdate = new ArrayList<>();

        // update scheduling info
        for (Notification n : dueNotifs) {
            boolean notifToBeDeleted = true;
            String repeatInterval = n.getRepeatInterval();
            if (repeatInterval != null && !repeatInterval.isEmpty()) {
                Instant[] nextRuns = n.getNextRuns();
                Instant[] finalRuns = n.getFinalRuns();
                
                // Update all due nextRuns and check if any are still valid
                for (int i = 0; i < nextRuns.length; i++) {
                    if (nextRuns[i].isBefore(curTime)) {
                        // This nextRun is due, try to update it
                        // Continue advancing while it's still due AND hasn't passed the final run
                        while (nextRuns[i].isBefore(curTime) && !nextRuns[i].isAfter(finalRuns[i])) {
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
                        }
                    }
                    
                    // Check if this nextRun is still valid (before or at its finalRun, and in the future)
                    if (nextRuns[i].isBefore(finalRuns[i]) || 
                        (nextRuns[i].equals(finalRuns[i]) && !nextRuns[i].isBefore(curTime))) {
                        notifToBeDeleted = false;
                    }
                }
                
                if (!notifToBeDeleted) {
                    n.setNextRuns(nextRuns);
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
