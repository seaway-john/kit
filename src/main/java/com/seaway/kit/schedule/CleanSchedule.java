package com.seaway.kit.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class CleanSchedule implements CommandLineRunner {

    private final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public CleanSchedule(ScheduledThreadPoolExecutor scheduledThreadPoolExecutor) {
        this.scheduledThreadPoolExecutor = scheduledThreadPoolExecutor;
    }

    @Override
    public void run(String... args) throws Exception {
        schedule();
    }

    private void schedule() {
        Runnable r = () -> {
            try {
                log.info("Start clean schedule");
            } catch (Exception e) {
                log.error("Exception in clean schedule, reason {}", e.getMessage());
            } finally {
                schedule();
            }
        };

        scheduledThreadPoolExecutor.schedule(r, 60, TimeUnit.SECONDS);
    }
}
