package com.example.petdrop.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.petdrop.model.Reminder;
import com.example.petdrop.repository.ReminderRepository;

@Service
public class ReminderScheduler {

    private final ReminderRepository repo;
    private final ExpoPushService expoPushService;

    public ReminderScheduler(ReminderRepository repo, ExpoPushService expoPushService) {
        this.repo = repo;
        this.expoPushService = expoPushService;
    }

    @Scheduled(fixedRate = 60000)
    public void processReminders() {
        List<Reminder> dueNotifs = repo.findDueReminders(LocalDateTime.now());
        if (dueNotifs.isEmpty()) {
            return;
        }

        // send all due reminders in chunks
        expoPushService.sendPushBatch(dueNotifs);

        // update scheduling info
        for (Reminder n : dueNotifs) {
            if (n.getRepeatInterval() != null && (n.getRemainingRepeats() == null || n.getRemainingRepeats() > 1)) {
                n.setNextRun(n.getNextRun().plus(n.getRepeatInterval()));
                if (n.getRemainingRepeats() != null) {
                    n.setRemainingRepeats(n.getRemainingRepeats() - 1);
                }
            } else {
                repo.delete(n);
                dueNotifs.remove(n);
            }
        }
        repo.saveAll(dueNotifs);
    }

}
