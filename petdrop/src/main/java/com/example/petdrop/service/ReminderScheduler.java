package com.example.petdrop.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
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
        ZonedDateTime curTime = ZonedDateTime.now();
        List<Reminder> dueNotifs = repo.findDueReminders(curTime);
        if (dueNotifs.isEmpty()) {
            return;
        }

        // send all due reminders in chunks
        expoPushService.sendPushBatch(dueNotifs);

        List<Reminder> toDelete = new ArrayList<>();
        List<Reminder> toUpdate = new ArrayList<>();

        // update scheduling info
        for (Reminder n : dueNotifs) {
            if (n.getRepeatInterval() != 0) {
                ZonedDateTime[] nextRuns = n.getNextRuns();
                for (int i = 0; i < nextRuns.length; i++) {
                    if (nextRuns[i].isBefore(curTime)) {
                        if (nextRuns[i].isBefore(n.getFinalRuns()[i])) {
                            nextRuns[i] = nextRuns[i].plusMinutes(n.getRepeatInterval());
                            n.setNextRuns(nextRuns);
                        }
                        break;
                    }
                }
                toUpdate.add(n);
            } else {
                toDelete.add(n);
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
