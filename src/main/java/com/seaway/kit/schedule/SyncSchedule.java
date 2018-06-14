package com.seaway.kit.schedule;

import com.seaway.kit.task.SyncTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SyncSchedule {

    private final SyncTask syncTask;

    public SyncSchedule(SyncTask syncTask) {
        this.syncTask = syncTask;
    }

    @Scheduled(initialDelay = 10000, fixedRate = 60000)
    protected void schedule() {
        try {
            log.info("Sync schedule");
            syncTask.clean();
        } catch (Exception e) {
            log.error("Exception in sync schedule, reason {}", e.getMessage());
        }
    }

}
