package com.seaway.kit.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SyncTask {

    @Async
    public void clean() {
        log.info("Clean Task");
    }

}
