package com.example.petdrop.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
        try {
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
                                    // Instant doesn't support WEEKS, so use 7 days instead
                                    nextRuns[i] = nextRuns[i].plus(7, ChronoUnit.DAYS);
                                    break;
                                case "monthly":
                                    // Instant doesn't support MONTHS, convert to LocalDateTime, add month, then convert back
                                    LocalDateTime ldt = LocalDateTime.ofInstant(nextRuns[i], ZoneId.systemDefault());
                                    ldt = ldt.plusMonths(1);
                                    nextRuns[i] = ldt.atZone(ZoneId.systemDefault()).toInstant();
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
        } catch (Exception e) {
            // Log error but don't crash the application
            System.err.println("[ERROR] Failed to process notifications: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("[ERROR] Cause: " + e.getCause().getMessage());
            }
            // Don't rethrow - allow the application to continue running
        }
    }

}
