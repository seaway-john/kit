package com.seaway.kit.pojo.mongo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseMongoEntity {
    protected String id;
    protected LocalDateTime scanTimestamp;

    public BaseMongoEntity() {
        this.scanTimestamp = LocalDateTime.now();
    }
}
