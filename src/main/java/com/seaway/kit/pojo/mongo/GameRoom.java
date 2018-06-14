package com.seaway.kit.pojo.mongo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GameRoom extends BaseMongoEntity {
    private String name;
    private String type;
    private int maxMember;
}
